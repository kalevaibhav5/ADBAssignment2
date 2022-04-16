package com.adb.adbassignment2.repository;

import com.adb.adbassignment2.domain.PubMed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PubMedRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void bulkInsert(List<PubMed> pubMeds){
        String sql = "INSERT INTO "
                + "adb.PubMed "
                + "(pmid, article_title,first_author,publisher,published_date,uploader) "
                + "VALUES " + "(?,?,?,?,?,?) on conflict (pmid) do nothing ";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {

                PubMed pubMed = pubMeds.get(i);
                ps.setInt(1, pubMed.getPmid());
                ps.setString(2, pubMed.getArticle_title());
                ps.setString(3, pubMed.getFirst_author());
                ps.setString(4, pubMed.getPublisher());
                ps.setDate(5, pubMed.getPublishedDate());
                ps.setString(6, pubMed.getUploader());

            }

            @Override
            public int getBatchSize() {
                return pubMeds.size();
            }
        });
    }
}
