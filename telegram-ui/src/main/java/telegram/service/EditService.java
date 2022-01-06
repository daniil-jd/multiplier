package telegram.service;

import ru.example.common.dto.business.expenses_kind.ExpensesCategory;
import ru.example.common.dto.business.expenses_kind.ExpensesKindRequestDto;
import org.springframework.stereotype.Service;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.DefaultBotOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EditService {
    private RequestToBaseService requestToBaseService;
    private Map<Integer, String> userEditAnswers = new HashMap<>();
    private ExpensesKindRequestDto expensesKindRequest = new ExpensesKindRequestDto();

    private final String PART_1 = "trata_or_postuplenie";
    private final String PART_2_TR = "category";
    private final String PART_2_PO = "post_sum";
    private final String PART_3_TR = "payment_sum";
    private final String PART_4_TYPE_CARD = "payment_type_card";
    private final String PART_4_TYPE_CASH = "payment_type_cash";

    private final String EDIT_PURPOSE = "edit_purpose_";
    private final String EDIT_PART_1 = "Выберите тип услуги:";
    private final String EDIT_PART_2 = "Введите название нового вида траты:";

    public EditService(String botToken, String botUsername, DefaultBotOptions botOptions) {
//        super(botToken, botUsername, botOptions);
        requestToBaseService = new RequestToBaseService(botOptions);
    }

    public void processEditAnswersWithChooseOption(String data, SilentSender silent, Long chatId) {
        if (data.startsWith(EDIT_PURPOSE) ) {
            userEditAnswers.put(1, getPurposeFromAnswer(data));
            expensesKindRequest.setPurpose(getPurposeFromAnswer(data));
            silent.execute(prepareEditPart2(chatId));
        }
    }

    public SendMessage prepareEditPart1(Long chatId) {
        userEditAnswers = new HashMap<>();
        expensesKindRequest = new ExpensesKindRequestDto();

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        ExpensesCategory[] categories = ExpensesCategory.values();
        Arrays.asList(categories).forEach(category -> {
                    List<InlineKeyboardButton> rowInline = new ArrayList<>();
                    rowInline.add(
                            new InlineKeyboardButton()
                                    .setText(category.name())
                                    .setCallbackData(EDIT_PURPOSE.concat(category.name())));
                    rowsInline.add(rowInline);
                }
        );

        markupInline.setKeyboard(rowsInline);

        SendMessage smsg = new SendMessage();
        smsg.setChatId(chatId);
        smsg.setText(EDIT_PART_1);
        smsg.enableMarkdown(false);
        smsg.setReplyMarkup(markupInline);

        return smsg;
    }

    public SendMessage prepareEditPart2(Long chatId) {
        SendMessage smsg = new SendMessage();
        smsg.setChatId(chatId);
        smsg.setText(EDIT_PART_2);
        smsg.enableMarkdown(false);

        return smsg;
    }

    public void processEditAnswerWithNewCategoryName(Update update, SilentSender silent) {
        if (expensesKindRequest.getPurpose() != null && expensesKindRequest.getName() == null) {
            String textName = update.getMessage().getText();
            userEditAnswers.put(2, textName);
            expensesKindRequest.setName(textName);
            silent.execute(prepareEditPart4(update.getMessage().getChatId()));
        }
    }

    private SendMessage prepareEditPart4(Long chatId) {
        String result = requestToBaseService.createExpensesKind(expensesKindRequest);
        String message = "Новый вид траты успешно добавлен!";
        if (!result.isEmpty() && result.contains("message")) {
            message = getErrorMessageFromExpensesKindCreateResponse(result);
        }

        SendMessage smsg = new SendMessage();
        smsg.setChatId(chatId);
        smsg.setText(message);
        smsg.enableMarkdown(false);

        return smsg;
    }

    private String getPurposeFromAnswer(String answer) {
        return answer.replace(EDIT_PURPOSE, "");
    }

    private String getErrorMessageFromExpensesKindCreateResponse(String rawMessage) {
        String messageText = "message";
        int end = rawMessage.indexOf("errors") - 3;
        int start = rawMessage.indexOf(messageText) + messageText.length() + 3;
        return rawMessage.substring(start, end);
    }
}
