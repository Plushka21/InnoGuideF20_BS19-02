package com.example.innoguidesjava;

import java.sql.*;

public class functions {

    static boolean addPlace(Connection c, String name, String owner, String br, String description, String address, String acoordinates) {
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM place;");
            rs.next();
            int index = rs.getInt("count") + 1;
            String sql = String.format("INSERT INTO place (ID, pname, owner, build_root, description, address, acoordinates) VALUES (%d, '%s', '%s', '%s', '%s', '%s', '%s'" +
                    ");", index, name, owner, br, description, address, acoordinates);
            stmt.executeUpdate(sql);
            rs.close();
            stmt.close();
            c.commit();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    static boolean addPlace(Connection c, String name, String acoordinates) {
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM place;");
            rs.next();
            int index = rs.getInt("count") + 1;
            String sql = String.format("INSERT INTO place (ID, pname, acoordinates) VALUES (%d, '%s', '%s');", index, name, acoordinates);
            stmt.executeUpdate(sql);
            rs.close();
            stmt.close();
            c.commit();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    static boolean addPlace(Connection c, String name, String owner, String description, String address, String acoordinates) {
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM place;");
            rs.next();
            int index = rs.getInt("count") + 1;
            String sql = String.format("INSERT INTO place (ID, pname, owner, description, address, acoordinates) VALUES (%d, '%s', '%s', '%s', '%s', '%s'" +
                    ") on conflict do nothing ;", index, name, owner, description, address, acoordinates);
            stmt.executeUpdate(sql);
            rs.close();
            stmt.close();
            c.commit();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    static boolean addCategory(Connection c, String name, String icon, String colour) {
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM category_list;");
            rs.next();
            int index = rs.getInt("count") + 1;
            String sql = String.format(
                    "INSERT INTO category_list (ID, cname, number_of_places, icon, colour) " +
                            "VALUES (%d, '%s', %d, '%s', '%s') on conflict do nothing ;", index, name, 1, icon, colour);
            stmt.executeUpdate(sql);
            rs.close();
            stmt.close();
            c.commit();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    static boolean addCategory(Connection c, String name) {
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM category_list;");
            rs.next();
            int index = rs.getInt("count") + 1;
            String sql = String.format(
                    "INSERT INTO category_list (ID, cname, number_of_places) VALUES (%d, '%s', %d" +
                    ") on conflict do nothing;", index, name, 1);
            stmt.executeUpdate(sql);
            rs.close();
            stmt.close();
            c.commit();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    static boolean addPlaceToCategory(Connection c, String cname, String pname) {
        try {
            Statement stmt = c.createStatement();
            String sql = String.format(
                    "INSERT INTO category_list_places (cname, pname)" +
                            " VALUES ('%s', '%s') on conflict do nothing;",cname ,pname);
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    static boolean addEvent(Connection c, String ename, String edate, String etime, String eplace, String poster) {
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM events;");
            rs.next();
            int index = rs.getInt("count") + 1;
            String sql = String.format(
                    "INSERT INTO events (ID, ename, edate, etime, event_place, poster)" +
                            " VALUES (%d, '%s','%s','%s','%s','%s'" +
                            ") on conflict do nothing;", index, ename, edate, etime, eplace, poster);
            stmt.executeUpdate(sql);
            rs.close();
            stmt.close();
            c.commit();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    static boolean addEvent(Connection c, String ename, String edate, String etime, String eplace) {
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM events;");
            rs.next();
            int index = rs.getInt("count") + 1;
            String sql = String.format(
                    "INSERT INTO events (ID, ename, edate, etime, event_place)" +
                            " VALUES (%d, '%s','%s','%s','%s'" +
                            ") on conflict do nothing;", index, ename, edate, etime, eplace);
            stmt.executeUpdate(sql);
            rs.close();
            stmt.close();
            c.commit();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    static boolean addFeedback(Connection c, String pname, double rating, String feedback) {
        try {
            Statement stmt = c.createStatement();
            String sql = String.format(
                    "INSERT INTO feedback (place_name, rating, feedback)" +
                            " VALUES ('%s', %s, '%s'" +
                            ") on conflict do nothing;", pname, String.valueOf(rating), feedback);
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    static boolean addFeedback(Connection c, String pname, double rating) {
        return addFeedback(c,pname,rating,"");
    }

    static boolean addOwnerContact(Connection c, String pname, String contact_details) {
        try {
            Statement stmt = c.createStatement();
            String sql = String.format(
                    "INSERT INTO owner_contacts (pname, contact_details)" +
                            " VALUES ('%s', '%s'" +
                            ") on conflict do nothing;", pname, contact_details);
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    static boolean addPhoto(Connection c, String place_name, String photo) {
        try {
            Statement stmt = c.createStatement();
            String sql = String.format(
                    "INSERT INTO photo (place_name, photo)" +
                            " VALUES ('%s', '%s'" +
                            ") on conflict do nothing;", place_name, photo);
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    static boolean addPlaceWorkingDays(Connection c, String pname, String working_days) {
        try {
            Statement stmt = c.createStatement();
            String sql = String.format(
                    "INSERT INTO place_working_days (pname, working_days)" +
                            " VALUES ('%s', '%s'" +
                            ") on conflict do nothing;", pname, working_days);
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    static boolean addPlaceWorkingTime(Connection c, String pname, String working_time) {
        try {
            Statement stmt = c.createStatement();
            String sql = String.format(
                    "INSERT INTO place_working_days (pname, working_time)" +
                            " VALUES ('%s', '%s'" +
                            ") on conflict do nothing;", pname, working_time);
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    static ResultSet getMeanPlaceRating(Connection c) {
        try {
            ResultSet rs;
            Statement stmt = c.createStatement();
            String sql = String.format(
                    "SELECT pname, avg(f.rating) as avarage_rating FROM place\n" +
                            "join feedback f on place.pname = f.place_name\n" +
                            "group by pname\n" +
                            "order by avarage_rating DESC;");
            rs = stmt.executeQuery(sql);
            stmt.close();
            c.commit();
            return rs;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    static void printMeanPlaceRating(Connection c) {
        try {
            ResultSet x;
            x = getMeanPlaceRating(c);
            while (x.next()) {
                String pname = x.getString("pname");
                String avarage_rating = x.getString("avarage_rating");
                System.out.println(String.format("Place name = %s, Rating = %s", pname, avarage_rating));
            }
            x.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    static boolean delPlace(Connection c,String pname) {
        try {
            Statement stmt = c.createStatement();
            String sql = String.format(
                    "DELETE FROM place\n" +
                            "    where pname = '%s';", pname);
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    static boolean delPhoto(Connection c,String photo) {
        try {
            Statement stmt = c.createStatement();
            String sql = String.format(
                    "DELETE FROM photo\n" +
                            "    where photo = '%s';", photo);
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    static boolean delCategoryList(Connection c, String cname) {
        try {
            Statement stmt = c.createStatement();
            String sql = String.format(
                    "DELETE FROM category_list\n" +
                            "    where cname = '%s';", cname);
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
