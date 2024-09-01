package com.example.dev_pro.botapi;

import com.example.dev_pro.service.handlers.InputMessageHandlerDogShelter;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BotStateContextDogShelterTest {

    private BotStateContextDogShelter botStateContextDogShelter;

    @Mock
    private InputMessageHandlerDogShelter fillingProfileHandler;

    @Mock
    private InputMessageHandlerDogShelter sendPhotoReportHandler;

    @Mock
    private InputMessageHandlerDogShelter defaultHandler;

    @Mock
    private Message message;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(fillingProfileHandler.getHandlerName()).thenReturn(BotStateDogShelter.FILLING_PROFILE);
        when(sendPhotoReportHandler.getHandlerName()).thenReturn(BotStateDogShelter.SEND_PHOTO_REPORT);
        when(defaultHandler.getHandlerName()).thenReturn(BotStateDogShelter.INFO_COM);

        botStateContextDogShelter = new BotStateContextDogShelter(List.of(fillingProfileHandler, sendPhotoReportHandler, defaultHandler));
    }

    @Test
    public void testProcessInputMessageWhenCurrentStateIsFillingProfileThenReturnSendMessage() {
        SendMessage expectedSendMessage = new SendMessage(12345L, "Profile is being filled");
        when(fillingProfileHandler.handle(message)).thenReturn(expectedSendMessage);

        SendMessage actualSendMessage = botStateContextDogShelter.processInputMessage(BotStateDogShelter.FILLING_PROFILE, message);

        assertEquals(expectedSendMessage, actualSendMessage);
    }

    @Test
    public void testProcessInputMessageWhenCurrentStateIsSendPhotoReportThenReturnSendMessage() {
        SendMessage expectedSendMessage = new SendMessage(12345L, "Photo report is being sent");
        when(sendPhotoReportHandler.handle(message)).thenReturn(expectedSendMessage);

        SendMessage actualSendMessage = botStateContextDogShelter.processInputMessage(BotStateDogShelter.SEND_PHOTO_REPORT, message);

        assertEquals(expectedSendMessage, actualSendMessage);
    }

    @Test
    public void testProcessInputMessageWhenCurrentStateIsNotFillingProfileOrSendPhotoReportThenReturnSendMessage() {
        SendMessage expectedSendMessage = new SendMessage(12345L, "Default handler response");
        when(defaultHandler.handle(message)).thenReturn(expectedSendMessage);

        SendMessage actualSendMessage = botStateContextDogShelter.processInputMessage(BotStateDogShelter.INFO_COM, message);

        assertEquals(expectedSendMessage, actualSendMessage);
    }
}