/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package ws;

import entidades.Producto;
import java.util.Arrays;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Paulina Cortez Alamilla.
 */
@Path("producto")
public class ProductoResource {

//    @Context
//    private UriInfo context;

    private Producto[] productos;

    
    public ProductoResource() {
         
         productos = new Producto[] {
            new Producto(1, "Producto1"),
            new Producto(2, "Producto2"),
            new Producto(3, "Producto3")
        };
    }

    /**
     * Retrieves representation of an instance of ws.ProductoResource
     * @return an instance of entidades.ProductoResource
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson() {
        return Response.status(Response.Status.OK).entity(productos).build();      
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJsonById(@PathParam("id") int id) {
        // Buscamos el producto con el id especificado
        Producto productoEncontrado = null;
        for (Producto producto : productos) {
            if (producto.getId() == id) {
                productoEncontrado = producto;
                break;
            }
        }
        // Si no se encuentra el producto, se devuelve una respuesta con código 404 Not Found
        if (productoEncontrado == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // Si se encuentra el producto, se devuelve una respuesta con código 200 OK y el producto en formato JSON
        return Response.status(Response.Status.OK).entity(productoEncontrado).build();
    }

    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postJson(Producto producto) {
        Producto[] nuevosProductos = Arrays.copyOf(productos, productos.length + 1);
        nuevosProductos[nuevosProductos.length - 1] = producto;
        productos = nuevosProductos;
        return Response.status(Response.Status.CREATED).entity(producto).build();
    }

    /**
     * PUT method for updating or creating an instance of ProductoResource
     * @param id
     * @param producto
     * @return 
     */
    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putJson(@PathParam("id") int id, Producto producto) {
        for (int i = 0; i < productos.length; i++) {
            if (productos[i].getId() == id) {
                productos[i] = producto;
                return Response.status(Response.Status.OK).entity(producto).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteJson(@PathParam("id") int id) {
        for (int i = 0; i < productos.length; i++) {
            if (productos[i].getId() == id) {
                Producto[] nuevosProductos = new Producto[productos.length - 1];
                System.arraycopy(productos, 0, nuevosProductos, 0, i);
                System.arraycopy(productos, i + 1, nuevosProductos, i, productos.length - i - 1);
                productos = nuevosProductos;
                return Response.status(Response.Status.OK).entity("Se ha eliminado el producto con el Id " + (i+1)).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
