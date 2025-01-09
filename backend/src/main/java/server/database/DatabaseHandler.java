package server.database;

import server.RestServer;
import server.chatroom.Chatroom;
import server.client.Client;
import server.message.Message;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class DatabaseHandler {
    private static DatabaseHandler instance = null;

    private DatabaseHandler() {}

    public Connection connect() {
        String url = "jdbc:mysql://localhost:3306/chatterplay";
        String username = "db_chatterPlay";
        String password = "chatterPlay24!";

        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            RestServer.log("Could not connect to db");
            return null;
        }
    }

    public static DatabaseHandler getInstance() {
        if(instance == null) {
            instance = new DatabaseHandler();
        }
        return instance;
    }

    public Client getClient(String id) {
        try {
            String sql = "SELECT * FROM user WHERE id = ?";
            PreparedStatement preparedStatement = this.connect().prepareStatement(sql);

            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()) {
                return getClientFromResultSet(rs);
            }

            return null;
        } catch (Exception e) {
            RestServer.log("Could not get client");
            return null;
        }
    }

    public Client[] getClients() {
        try {
            String sql = "SELECT * FROM user";
            PreparedStatement preparedStatement = this.connect().prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            ArrayList<Client> clients = new ArrayList<>();
            while(rs.next()) {
                clients.add(getClientFromResultSet(rs));
            }

            return clients.toArray(new Client[0]);
        } catch (Exception e) {
            RestServer.log("Could not get clients");
            return new Client[0];
        }
    }

    private Client getClientFromResultSet(ResultSet rs) throws SQLException {
        return new Client(rs.getString("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("origin"),
                rs.getDate("joined")
        );
    }

    public void updateClient(Client client) {
        try {
            String sql = "UPDATE user SET first_name = ?, last_name = ?, origin = ? WHERE id = ?";
            PreparedStatement preparedStatement = this.connect().prepareStatement(sql);

            preparedStatement.setString(1, client.getFirstName());
            preparedStatement.setString(2, client.getLastName());
            preparedStatement.setString(3, client.getOrigin());
            preparedStatement.setString(4, client.getId());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            RestServer.log("Could not update client with id " + client.getId());
        }
    }

    public void removeClient(String clientId) {
        try {
            String sql = "DELETE FROM user WHERE id = ?";
            PreparedStatement preparedStatement = this.connect().prepareStatement(sql);
            preparedStatement.setString(1, clientId);
            preparedStatement.executeUpdate();

            RestServer.log("Removed client with id: " + clientId);
        } catch (Exception e) {
            RestServer.log("Could not remove client with id " + clientId);
        }
    }

    public void addClientToChat(String chatId, String clientId) {
        try {
            String sql = "INSERT INTO chat_member (id, chat_id, user_id) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = this.connect().prepareStatement(sql);

            preparedStatement.setString(1, generateId(6));
            preparedStatement.setString(2, chatId);
            preparedStatement.setString(3, clientId);

            preparedStatement.executeUpdate();
            RestServer.log("User '" + clientId + "' joined chatroom with id " + chatId);
        } catch (Exception e) {
            RestServer.log("User '" + clientId + "' could not join chatroom with id " + chatId);
        }
    }

    public void addMessage(Message message) {
        try {
            String sql = "INSERT INTO messages (id, chat_id, sender_id, content, timestamp) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = this.connect().prepareStatement(sql);

            preparedStatement.setString(1, message.getId());
            preparedStatement.setString(2, message.getChat().getId());
            preparedStatement.setString(3, message.getSender().getId());
            preparedStatement.setString(4, message.getContent());
            preparedStatement.setString(5, message.getTimestamp().toString());

            preparedStatement.executeUpdate();
            RestServer.log("Send message '" + message.getContent() + "' to chatroom with id " + message.getChat().getId());
        } catch (Exception e) {
            RestServer.log("Could not send message '" + message.getContent() + "'");
        }
    }

    public Message[] getMessages(String chatId) {
        try {
            String sql = "SELECT * FROM messages WHERE chat_id = ?";
            PreparedStatement preparedStatement = this.connect().prepareStatement(sql);

            preparedStatement.setString(1, chatId);
            ResultSet rs = preparedStatement.executeQuery();

            ArrayList<Message> messages = new ArrayList<>();
            while(rs.next()) {
                Message message = new Message(
                        rs.getString("id"),
                        rs.getString("chat_id"),
                        rs.getString("sender_id"),
                        rs.getString("content"),
                        rs.getTimestamp("timestamp")
                );
                messages.add(message);
            }

            return messages.toArray(new Message[0]);
        } catch (Exception e) {
            RestServer.log("Could not get messages from chatroom with id " + chatId);
            return new Message[0];
        }
    }

    public Chatroom[] getChatrooms() {
        try {
            String sql = "SELECT * FROM chatrooms";
            Statement stmt = this.connect().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            ArrayList<Chatroom> chatrooms = new ArrayList<>();
            while(rs.next()) {
                Chatroom chat = getChatroomByResultSet(rs);

                if(chat != null) {
                    chatrooms.add(chat);
                }
            }

            return chatrooms.toArray(new Chatroom[0]);
        } catch (Exception e) {
            RestServer.log("Could not get chatrooms");
            return new Chatroom[0];
        }
    }

    private Chatroom getChatroomByResultSet(ResultSet rs) {
        try {
            String sql = "SELECT user_id FROM chat_member WHERE chat_id = ?";
            PreparedStatement preparedStatement = this.connect().prepareStatement(sql);

            preparedStatement.setString(1, rs.getString("id"));
            ResultSet memberRs = preparedStatement.executeQuery();

            ArrayList<String> memberIds = new ArrayList<>();
            while(memberRs.next()) {
                memberIds.add(memberRs.getString("user_id"));
            }

            return new Chatroom(rs.getString("id"), rs.getString("name"), memberIds.toArray(new String[0]));
        } catch (Exception e) {
            RestServer.log("Could not get chatroom");
            return null;
        }
    }

    public void createChatroom(Chatroom chat) {
        try {
            String sql = "INSERT INTO chatrooms (id, name) VALUES (?, ?)";
            PreparedStatement preparedStatement = this.connect().prepareStatement(sql);

            preparedStatement.setString(1, chat.getId());
            preparedStatement.setString(2, chat.getName());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            RestServer.log("Could not create chatroom " + chat.getName());
        }
    }

    public void removeChatroom(String chatId) {
        try {
            String sql = "DELETE FROM chatrooms WHERE id = ?";
            PreparedStatement preparedStatement = this.connect().prepareStatement(sql);
            preparedStatement.setString(1, chatId);
            preparedStatement.executeUpdate();

            RestServer.log("Removed chatroom with id: " + chatId);
        } catch (Exception e) {
            RestServer.log("Could not remove chatroom with id " + chatId);
        }
    }

    public static String generateId(int length) {
        try {
            long time = System.nanoTime();
            Random random = new Random();

            long randomValue = random.nextLong();
            String input = time + String.valueOf(randomValue);

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.substring(0, length);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No such algorithm");
            return "";
        }

    }
}
