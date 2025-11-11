package com.anishshinde;

import com.anishshinde.thread.Player;
import com.anishshinde.thread.PlayerInitiator;
import org.junit.Test;
import org.mockito.InOrder;
import static org.mockito.Mockito.*;

public class TestPlayerInitiator {

    @Test
    public void initiatorShouldSendInitialMessageThenWaitForResponseBeforeSendingNext() throws InterruptedException {
        Player mockPlayer = mock(Player.class);
        when(mockPlayer.getMaxMessages()).thenReturn(2);
        when(mockPlayer.isInitiator()).thenReturn(true);

        when(mockPlayer.takeMessage())
                .thenReturn("response 1")
                .thenReturn("response 2");

        PlayerInitiator playerInitiator = new PlayerInitiator(mockPlayer);
        playerInitiator.startPlayerInitiator();

        InOrder inOrder = inOrder(mockPlayer);
        // initial message
        inOrder.verify(mockPlayer).sendMessage(anyString());

        inOrder.verify(mockPlayer).takeMessage();
        inOrder.verify(mockPlayer).sendMessage(anyString());
        inOrder.verify(mockPlayer).takeMessage();

        verify(mockPlayer, times(2)).sendMessage(anyString());
        verify(mockPlayer, times(2)).takeMessage();
    }

}
