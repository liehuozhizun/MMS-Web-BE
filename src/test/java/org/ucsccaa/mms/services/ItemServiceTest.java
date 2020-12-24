package org.ucsccaa.mms.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.ucsccaa.mms.MembershipManagementSystemApplication;
import org.ucsccaa.mms.domains.Item;
import org.ucsccaa.mms.repositories.ItemRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MembershipManagementSystemApplication.class)
public class ItemServiceTest {
    @Mock
    private ItemRepository repository;
    @InjectMocks
    private ItemService service;

    @Test
    public void addItemTest() {
        Item expectedItem = new Item();
        when(repository.save(any())).thenReturn(expectedItem);
        Long id = service.addItem(expectedItem);
        Assert.assertEquals(expectedItem.getId(), id);
    }

    @Test(expected = RuntimeException.class)
    public void addItemNullTest() {
        service.addItem(null);
    }

    @Test
    public void updateItemTest() {
        Item expectedItem = new Item();
        expectedItem.setId((long)1);
        expectedItem.setName("name");

        when(repository.existsById(anyLong())).thenReturn(true);
        when(repository.save(any())).thenReturn(expectedItem);
        Item item = service.updateItem(expectedItem);
        Assert.assertNotNull(item);
        Assert.assertEquals(expectedItem.getId(), item.getId());
        Assert.assertEquals(expectedItem.getName(), item.getName());
    }

    @Test
    public void updateItemNotFoundTest() {
        when(repository.existsById(anyLong())).thenReturn(false);
        Item item = service.updateItem(new Item());
        Assert.assertEquals(null, item);
    }

    @Test(expected = RuntimeException.class)
    public void updateItemNullTest() {
        service.updateItem(null);
    }

    @Test
    public void getItemTest() {
        Item expectedItem = new Item();
        expectedItem.setId((long)1);
        expectedItem.setName("name");

        when(repository.findById(expectedItem.getId())).thenReturn(Optional.of(expectedItem));
        Item item = service.getItem(expectedItem.getId());
        Assert.assertEquals(expectedItem.getId(), item.getId());
        Assert.assertEquals(expectedItem.getName(), item.getName());
    }

    @Test
    public void getItemNotFoundTest() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Item item = service.getItem(anyLong());
        Assert.assertEquals(null, item);
    }

    @Test(expected = RuntimeException.class)
    public void getItemNullTest() {
        service.getItem(null);
    }

    @Test
    public void deleteItemTest() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(new Item()));
        boolean result = service.deleteItemById((long)1);
        Assert.assertEquals(true, result);
    }

    @Test
    public void deleteItemNotFoundTest() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        boolean result = service.deleteItemById((long)1);
        Assert.assertEquals(false, result);
    }

    @Test(expected = RuntimeException.class)
    public void deleteItemNullTest() {
        service.deleteItemById(null);
    }
}
