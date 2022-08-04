package com.ground.domain.board.dto;

import com.ground.domain.board.entity.BoardLike;
import com.ground.domain.board.entity.BoardSave;
import com.ground.domain.user.dto.UserProfileDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardLikeDto {
    private Long id;
    private BoardUserDto user;

    public BoardLikeDto(BoardLike entity) {
        this.id = entity.getId();
        this.user = new BoardUserDto(entity.getUser());
    }
}