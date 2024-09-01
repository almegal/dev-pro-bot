package com.example.dev_pro.botapi;

import com.example.dev_pro.service.handlers.InputMessageHandlerCatShelter;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BotStateContextCatShelterTest {

    private BotStateContextCatShelter botStateContextCatShelter;

    @Mock
    private InputMessageHandlerCatShelter fillingProfileHandler;

    @Mock
    private InputMessageHandlerCatShelter sendPhotoReportHandler;

    @Mock
    private InputMessageHandlerCatShelter defaultHandler;

    @Mock
    private Message message;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(fillingProfileHandler.getHandlerName()).thenReturn(BotStateCatShelter.FILLING_PROFILE);
        when(sendPhotoReportHandler.getHandlerName()).thenReturn(BotStateCatShelter.SEND_PHOTO_REPORT);
        when(defaultHandler.getHandlerName()).thenReturn(BotStateCatShelter.INFO_COM);

        botStateContextCatShelter = new BotStateContextCatShelter(List.of(fillingProfileHandler, sendPhotoReportHandler, defaultHandler));
    }

    @Test
    public void testProcessInputMessageWhenCurrentStateIsFillingProfileThenReturnSendMessage() {
        SendMessage expectedSendMessage = new SendMessage(12345L, "Profile is being filled");
        when(fillingProfileHandler.handle(message)).thenReturn(expectedSendMessage);

        SendMessage actualSendMessage = botStateContextCatShelter.processInputMessage(BotStateCatShelter.FILLING_PROFILE, message);

        assertEquals(expectedSendMessage, actualSendMessage);
    }

    @Test
    public void testProcessInputMessageWhenCurrentStateIsSendPhotoReportThenReturnSendMessage() {
        SendMessage expectedSendMessage = new SendMessage(12345L, "Photo report is being sent");
        when(sendPhotoReportHandler.handle(message)).thenReturn(expectedSendMessage);

        SendMessage actualSendMessage = botStateContextCatShelter.processInputMessage(BotStateCatShelter.SEND_PHOTO_REPORT, message);

        assertEquals(expectedSendMessage, actualSendMessage);
    }

    @Test
    public void testProcessInputMessageWhenCurrentStateIsNotFillingProfileOrSendPhotoReportThenReturnSendMessage() {
        SendMessage expectedSendMessage = new SendMessage(12345L, "Default handler response");
        when(defaultHandler.handle(message)).thenReturn(expectedSendMessage);

        SendMessage actualSendMessage = botStateContextCatShelter.processInputMessage(BotStateCatShelter.INFO_COM, message);

        assertEquals(expectedSendMessage, actualSendMessage);
    }
}