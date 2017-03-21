package com.pgs.dto;

import com.pgs.enums.ESocialType;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wkloc on 2017-03-21.
 */
public class SocialUserDTOTest {

    @Test
    public void shouldCreateDto() {
        SocialUserDTO dto = new SocialUserDTO("ID", "test@mail.com", "firstname", "lastname", "image", ESocialType.FACEBOOK);
        assertEquals("ID", dto.getId());
        assertEquals("test@mail.com", dto.getEmail());
        assertEquals("firstname", dto.getFirstname());
        assertEquals("lastname", dto.getLastname());
        assertEquals("image", dto.getImage());
        assertEquals(ESocialType.FACEBOOK, dto.getSocialType());
    }

    @Test
    public void shouldCreateEmptyDto() {
        SocialUserDTO dto = new SocialUserDTO();
        assertNull(dto.getId());
        assertNull(dto.getEmail());
        assertNull(dto.getFirstname());
        assertNull(dto.getLastname());
        assertNull(dto.getImage());
        assertNull(dto.getSocialType());
    }
}