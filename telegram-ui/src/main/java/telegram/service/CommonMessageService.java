package telegram.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;

@Service
public class CommonMessageService {

    public SendMessage prepareErrorMessage(Long chatId, String errorMessage) {
        SendMessage smsg = new SendMessage();
        smsg.setChatId(chatId);
        smsg.setText(errorMessage);
        smsg.enableMarkdown(false);

        return smsg;
    }
}
