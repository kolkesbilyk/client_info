package org.example.config.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.config.model.Client;
import org.example.config.service.ClientService;

@Path("/clients")
public class Controller {

    @Inject
    private ClientService service;

    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Client addClient(Client client){
        return service.save(client);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientById(@PathParam("id") Long id){
        if (service.getClientById(id).getFirstname() != null && service.isActive(id)) {
            return Response.ok().status(Response.Status.OK).entity(service.getClientById(id)).build();
        }else
            return Response.ok().status(400).entity("Client with id: " + id + " not found or deactivated").build();
    }

    @POST
    @Path("/insert/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Client updateClient(@PathParam("id") Long id, Client client){
        return service.update(id, client);
    }

    @DELETE
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteClient(@PathParam("id") Long id){
        if (service.getClientById(id).getFirstname() != null && service.isActive(id)) {
            service.delete(id);
            return Response.ok().status(Response.Status.OK).entity("Client with id: " + id + " was deactivated").build();
        }else return Response.ok().status(400).entity("Client with id: " + id + " not found or deactivated").build();
    }
}
