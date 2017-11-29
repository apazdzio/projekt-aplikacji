package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.trello.client.TrelloBoardDto;
import com.crud.tasks.trello.client.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloListDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TrelloMapperTestSuite {

    @InjectMocks
    private TrelloMapper trelloMapper;
    @Mock
    private List<TrelloListDto> trelloListDto;

    @Test
    public void shouldMapToBoards() {
        //Given
        List<TrelloBoardDto> trelloBoardsDto = new ArrayList<>();
        trelloBoardsDto.add(new TrelloBoardDto("1", "my_task", trelloListDto));
        //When
        List<TrelloBoard> mappedTrelloBoards = trelloMapper.mapToBoards(trelloBoardsDto);
        List<TrelloBoardDto> trelloBoardsDto2 = trelloMapper.mapToBoardsDto(mappedTrelloBoards);
        //Then
        Assert.assertEquals(1, mappedTrelloBoards.size());
        Assert.assertEquals("1", mappedTrelloBoards.get(0).getId());

        Assert.assertEquals(1, trelloBoardsDto2.size());
        Assert.assertEquals("1", trelloBoardsDto2.get(0).getId());
    }

    @Test
    public void shouldMapToList() {
        //Given
        List<TrelloList> trelloList = new ArrayList<>();
        trelloList.add(new TrelloList("1", "my_list", false));
        //When
        List<TrelloListDto> mappedTrelloListsDto = trelloMapper.mapToListDto(trelloList);
        List<TrelloList> trelloList2 = trelloMapper.mapToList(mappedTrelloListsDto);
        //Then
        Assert.assertEquals(1, trelloList2.size());
        Assert.assertEquals("my_list", trelloList2.get(0).getName());
        Assert.assertEquals(false, trelloList2.get(0).isClosed());

        Assert.assertEquals(1, mappedTrelloListsDto.size());
        Assert.assertEquals("my_list", mappedTrelloListsDto.get(0).getName());
        Assert.assertEquals(false, mappedTrelloListsDto.get(0).isClosed());
    }

    @Test
    public void shouldMapToCard(){
        //Given
        TrelloCardDto cardDto = new TrelloCardDto("card",  "test_card", "top", "123");
        //When
        TrelloCard card = trelloMapper.mapToCard(cardDto);
        TrelloCardDto cardDto2 = trelloMapper.mapToCardDto(card);
        //Then
        Assert.assertEquals("card", card.getName());
        Assert.assertEquals("123", card.getListId());

        Assert.assertEquals("card", cardDto2.getName());
        Assert.assertEquals("123", cardDto2.getListId());
    }
}
