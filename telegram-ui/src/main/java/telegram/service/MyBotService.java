package telegram.service;

import com.ibm.icu.text.Transliterator;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;

import static org.telegram.abilitybots.api.objects.Locality.USER;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

public class MyBotService extends AbilityBot {
    private final Transliterator toLatinTrans;
    private final String CYRILLIC_TO_LATIN = "Russian-Latin/BGN";
    private RequestToBaseService requestToBaseService;
    private AddService addService;
    private EditService editService;
    private CommonMessageService commonMessageService = new CommonMessageService();

    public MyBotService(String botToken, String botUsername, DefaultBotOptions botOptions) {
        super(botToken, botUsername, botOptions);
        toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN);
        requestToBaseService = new RequestToBaseService(botOptions);
        addService = new AddService(botToken, botUsername, botOptions);
        editService = new EditService(botToken, botUsername, botOptions);
    }

    public int creatorId() {
        return 0;
    }

    @Override
    public void onUpdateReceived(Update update) {
        //обработка введенных строк
        if (!update.hasCallbackQuery()) {
            addService.processAddAnswerWithMoney(update, silent);
            editService.processEditAnswerWithNewCategoryName(update, silent);
            super.onUpdateReceived(update);
        }
        //обработка выбранных вариантов
        else {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            Long chatId = callbackQuery.getMessage().getChatId();
            String data = callbackQuery.getData();

            addService.processAddAnswersWithChooseOption(data, silent, chatId);
            editService.processEditAnswersWithChooseOption(data, silent, chatId);
        }
    }

    public Ability getAddMessage() {
        return Ability
                .builder()
                .name("add")
                .locality(USER)
                .privacy(PUBLIC)
                .action(ctx -> silent.execute(addService.preparePart1Message(ctx)))
                .build();
    }

    public Ability getEditMessage() {
        return Ability
                .builder()
                .name("edit")
                .locality(USER)
                .privacy(PUBLIC)
                .action(ctx -> silent.execute(editService.prepareEditPart1(ctx.chatId())))
                .build();
    }
}
