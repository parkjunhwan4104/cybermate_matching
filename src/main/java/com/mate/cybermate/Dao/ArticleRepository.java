package com.mate.cybermate.Dao;

import com.mate.cybermate.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {
}
