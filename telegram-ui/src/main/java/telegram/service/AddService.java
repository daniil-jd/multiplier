package telegram.service;

import com.ibm.icu.text.Transliterator;
import ru.example.common.dto.business.expenses.ExpensesRequestDto;
import ru.example.common.dto.business.expenses_kind.ExpensesKindDto;
import org.springframework.stereotype.Service;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.DefaultBotOptions;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddService {
    private RequestToBaseService requestToBaseService;
    private final Transliterator toLatinTrans;
    private CommonMessageService commonMessageService = new CommonMessageService();

    private final String CYRILLIC_TO_LATIN = "Russian-Latin/BGN";
    private final String PART_1 = "trata_or_postuplenie";
    private final String PART_2_TR = "category";
    private final String PART_2_PO = "post_sum";
    private final String PART_3_TR = "payment_sum";
    private final String PART_4_TYPE_CARD = "payment_type_card";
    private final String PART_4_TYPE_CASH = "payment_type_cash";

    private final String PART_1_TEXT = "Трата или Пополнение?";
    private final String PART_1_A1 = "Трата";
    private final String PART_1_A2 = "Пополнение";

    private final String PART_2_TEXT = "Категория:";
    private List<ExpensesKindDto> PART_2_A;

    private final String PART_3_TEXT = "Введите Сумму Траты:";

    private final String PART_4_TEXT = "Тип оплаты:";
    private final String PART_4_A1 = "Карта";
    private final String PART_4_A2 = "Наличные";

    private Map<Integer, String> userAnswers = new HashMap<>();
    private ExpensesRequestDto userExpenses = new ExpensesRequestDto();

    public AddService(String botToken, String botUsername, DefaultBotOptions botOptions) {
//        super(botToken, botUsername, botOptions);
        toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN);
        requestToBaseService = new RequestToBaseService(botOptions);
    }

    public void processAddAnswerWithMoney(Update update, SilentSender silent) {
        if (userExpenses.getExpensesKind() != null && userExpenses.getCost() == null) {
            String textSum = update.getMessage().getText();
            try {
                Long.parseLong(textSum);
            } catch (NumberFormatException e) {
                silent.execute(commonMessageService.prepareErrorMessage(update.getMessage().getChatId(), "Неверный формат суммы."));
            }
            userAnswers.put(3, textSum);
            userExpenses.setCost(Long.parseLong(textSum));
            silent.execute(preparePart4Message(update.getMessage().getChatId()));
        }
    }

    public void processAddAnswersWithChooseOption(String data, SilentSender silent, Long chatId) {
        if (data.equalsIgnoreCase(PART_2_TR)) {
            userAnswers.put(1, PART_1_A1);
            userExpenses.setName(PART_1_A1);
            silent.execute(preparePart2Message(chatId));
        } else if (data.contains("answer_2")) {
            userAnswers.put(2, data);
            userExpenses.setExpensesKind(data.replace("answer_2_", ""));
            silent.execute(preparePart3Message(chatId));
        } else if (data.contains("payment_type")) {
            userAnswers.put(4, data);
            if (data.contains("card")) {
                userExpenses.setPaymentType(true);
            } else {
                userExpenses.setPaymentType(false);
            }
            silent.execute(preparePart5Message(chatId));
        }
    }

    public SendMessage preparePart1Message(MessageContext ctx) {
        userExpenses = new ExpensesRequestDto();
        userAnswers = new HashMap<>();

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText(PART_1_A1).setCallbackData(PART_2_TR));
        rowInline.add(new InlineKeyboardButton().setText(PART_1_A2).setCallbackData(PART_2_PO));
        // Set the keyboard to the markup
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);

        SendMessage smsg = new SendMessage();
        smsg.setChatId(ctx.chatId());
        smsg.setText(PART_1_TEXT);
        smsg.enableMarkdown(false);
        smsg.setReplyMarkup(markupInline);

        return smsg;
    }

    private SendMessage preparePart2Message(Long chatId) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        PART_2_A = requestToBaseService.getListFromBase("GET", "expenses/kind", null, ExpensesKindDto.class);
        PART_2_A.forEach(answer -> {
                    List<InlineKeyboardButton> rowInline = new ArrayList<>();
                    rowInline.add(
                            new InlineKeyboardButton()
                                    .setText(answer.getKind())
                                    .setCallbackData("answer_2_".concat(answer.getKind())));
                    rowsInline.add(rowInline);
                }
        );

        markupInline.setKeyboard(rowsInline);

        SendMessage smsg = new SendMessage();
        smsg.setChatId(chatId);
        smsg.setText(PART_2_TEXT);
        smsg.enableMarkdown(false);
        smsg.setReplyMarkup(markupInline);

        return smsg;
    }

    private SendMessage preparePart3Message(Long chatId) {
        SendMessage smsg = new SendMessage();
        smsg.setChatId(chatId);
        smsg.setText(PART_3_TEXT);
        smsg.enableMarkdown(false);

        return smsg;
    }

    private SendMessage preparePart4Message(Long chatId) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText(PART_4_A1).setCallbackData(PART_4_TYPE_CARD));
        rowInline.add(new InlineKeyboardButton().setText(PART_4_A2).setCallbackData(PART_4_TYPE_CASH));
        // Set the keyboard to the markup
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);

        SendMessage smsg = new SendMessage();
        smsg.setChatId(chatId);
        smsg.setText(PART_4_TEXT);
        smsg.enableMarkdown(false);
        smsg.setReplyMarkup(markupInline);

        return smsg;
    }

    private SendMessage preparePart5Message(Long chatId) {
        userExpenses.setCreated(Timestamp.valueOf(LocalDateTime.now()));

        SendMessage smsg = new SendMessage();
        smsg.setChatId(chatId);
        smsg.setText(userAnswers.toString() + " " + userExpenses);
        smsg.enableMarkdown(false);

        requestToBaseService.sendExpenses(userExpenses);

        return smsg;
    }
}
