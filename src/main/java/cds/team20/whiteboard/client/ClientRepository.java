package cds.team20.whiteboard.client;

public interface ClientRepository {
    void save(Client client);
    Client findByIP(String clientIP);
    void delete(Client client);
}
