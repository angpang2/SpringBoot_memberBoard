package com.its.memberboard.repository;

import com.its.memberboard.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits = b.boardHits + 1 where b.id = :id")
    void updateHits(@Param("id") Long id);

    @Modifying
    @Query(value = "update BoardEntity b set b.boardLike = b.boardLike + 1 where b.id = :boardId")
    void updateLike(@Param("boardId") Long boardId);

    List<BoardEntity> findByBoardTitleContainingOrBoardWriterContainingOrderByIdDesc(String title, String writer);
    List<BoardEntity> findByBoardWriterContainingOrderByIdDesc(String q);
    List<BoardEntity> findByBoardTitleContainingOrderByIdDesc(String q);

    Page<BoardEntity> findByBoardTitleContaining(String q, PageRequest id);

    Page<BoardEntity> findByBoardWriterContaining(String q, PageRequest id);

    Page<BoardEntity> findByBoardContentsContaining(String q, PageRequest id);
}
