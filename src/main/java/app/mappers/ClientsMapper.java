package app.mappers;

import app.domain.model.Client;
import app.mappers.dto.ClientDTO;

import java.util.ArrayList;
import java.util.List;

public class ClientsMapper {

    public ClientDTO toDTO(Client client) {
        return new ClientDTO(client.getClientsCitizenCardNumber(),
                client.getNhsNumber(), client.getBirthDate(), client.getSex()
                , client.getTinNumber(), client.getEmail(), client.getName(), client.getPhoneNumber());
    }

    public List<ClientDTO> toDTO(List<Client> clients) {
        List<ClientDTO> clientDTOS = new ArrayList<>();
        for (Client client : clients) {
            clientDTOS.add(this.toDTO(client));
        }
        return clientDTOS;
    }
}
