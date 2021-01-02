package org.ucsccaa.mms.services;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.ucsccaa.mms.repositories.OPTRepository;
import org.ucsccaa.mms.domains.OPT;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OPTServiceTest {
    @Mock
    private OPTRepository optRepository;

    @InjectMocks
    private OPTService optService;

    @Test
    public void findByIDTest() {
        OPT optExpect = new OPT();
        optExpect.setId((long) 1);
        optExpect.setStatus("STATUS");
        optExpect.setCardNumber("TEST-CARD-ID");

        when(optRepository.findById(optExpect.getId())).thenReturn(java.util.Optional.of(optExpect));
        Optional<OPT> optionalOPT = optService.findOPTByID(optExpect.getId());

        Assert.assertTrue(optionalOPT.isPresent());
        Assert.assertEquals(optExpect.getId(), optionalOPT.get().getId());
        Assert.assertEquals(optExpect.getStatus(), optionalOPT.get().getStatus());
        Assert.assertEquals(optExpect.getCardNumber(), optionalOPT.get().getCardNumber());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findByIDNULLArgTest() {
        optService.findOPTByID(null);
    }

    @Test
    public void findByIDEmptyTest() {
        when(optRepository.findById(any())).thenReturn(java.util.Optional.empty());
        Optional<OPT> opt = optService.findOPTByID((long) 1);
        Assert.assertFalse(opt.isPresent());
    }

    @Test
    public void updateTest() {
        OPT optExpect = new OPT();
        optExpect.setId((long) 1);
        optExpect.setStatus("STATUS");
        optExpect.setCardNumber("TEST-CARD-ID");

        when(optRepository.existsById(any())).thenReturn(true);
        when(optRepository.save(any())).thenReturn(optExpect);
        Optional<OPT> optResult = optService.updateOPTByID(optExpect);

        Assert.assertTrue(optResult.isPresent());
        Assert.assertEquals(optExpect.getId(), optResult.get().getId());
        Assert.assertEquals(optExpect.getStatus(), optResult.get().getStatus());
        Assert.assertEquals(optExpect.getCardNumber(), optResult.get().getCardNumber());
    }

    @Test
    public void updateNotFoundTest() {
        OPT optExpect = new OPT();
        optExpect.setId((long) 1);

        when(optRepository.existsById(optExpect.getId())).thenReturn(false);

        Optional<OPT> optResult = optService.updateOPTByID(optExpect);
        Assert.assertFalse(optResult.isPresent());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateNULLArgTest() {
        optService.updateOPTByID(new OPT());
//        optService.updateOPT(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteByIDNULLArgTest() {
        optService.deleteOPT(null);
    }

    @Test
    public void createUserTest() {
        OPT optExpect = new OPT();
        optExpect.setId((long) 1);
        optExpect.setStatus("STATUS");
        optExpect.setCardNumber("TEST-CARD-ID");

        when(optRepository.save(optExpect)).thenReturn(optExpect);

        Long result = optService.createOPT(optExpect);
        Assert.assertEquals(result, optExpect.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createUserNullTest() {
        optService.createOPT(null);
    }


}
