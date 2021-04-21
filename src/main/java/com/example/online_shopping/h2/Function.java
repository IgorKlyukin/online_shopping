package com.example.online_shopping.h2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Function {
    public static ResultSet getMatrix(Connection conn, int parentId) throws SQLException {
        String sql = "WITH RECURSIVE Recursii(children_id, parent_id) " +
                "AS " +
                "( " +
                "SELECT children_id, parent_id FROM public.t_category_parent " +
                "WHERE parent_id = " + parentId + " " +
                "UNION ALL " +
                "SELECT e.children_id, e.parent_id FROM public.t_category_parent e " +
                "JOIN Recursii r ON e.parent_id = r.children_id " +
                ") " +
                "select category0_.id as id, category0_.name as name " +
                "from t_category category0_ " +
                "left outer join t_category_parent parent1_ on category0_.id=parent1_.parent_id " +
                "left outer join t_category category2_ on parent1_.children_id=category2_.id " +
                "where category2_.id is null " +
                "and category0_.id in " +
                "(SELECT id FROM public.t_category " +
                "WHERE id in (SELECT children_id FROM Recursii) " +
                "UNION " +
                "SELECT id FROM public.t_category " +
                "WHERE id = " + parentId + ") " +
                "order by category0_.id asc;";
        return conn.createStatement().executeQuery(sql);
    }
}
