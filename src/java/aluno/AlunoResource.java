/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aluno;

import bd.core.*;
import bd.daos.Alunos;
import bd.dbos.Aluno;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author u17168
 */
@Path("aluno")
public class AlunoResource {
    private static final String NOME_ALUNO = "nome", RA_ALUNO = "RA", EMAIL_ALUNO = "email";
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AlunoResource
     */
    public AlunoResource() {
    }

    @GET
    @Path("/getAllAlunos")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllAlunos() {
        try {
        MeuResultSet meuResultSet = Alunos.getAlunos();
        
        //JsonObjectBuilder lista = Json.createObjectBuilder(JsonValue.EMPTY_JSON_OBJECT);
        JsonArrayBuilder lista = Json.createArrayBuilder();
        while (meuResultSet.next()) {
            String ra = meuResultSet.getString(RA_ALUNO);
            String nome = meuResultSet.getString(NOME_ALUNO);
            String email = meuResultSet.getString(EMAIL_ALUNO);
       
            lista.add(Json.createObjectBuilder().add(NOME_ALUNO, nome).add(EMAIL_ALUNO, email).add(RA_ALUNO, ra));
        }
        
        return lista.build().toString();
        }
        catch (Exception e) {
            throw new WebApplicationException(
Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
"Erro").build());
        }
    }
    
    @GET
    @Path("/getAlunoRA/{RA}")
    @Consumes (MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String getAlunoRA(@PathParam("RA") String RA) throws Exception {
        Aluno aluno = Alunos.getAluno(RA);
        
        JsonObjectBuilder alunoJSON = Json.createObjectBuilder(JsonValue.EMPTY_JSON_OBJECT);
        
        alunoJSON.add(NOME_ALUNO, aluno.getNome());
        alunoJSON.add(RA_ALUNO, aluno.getRA());
        alunoJSON.add(EMAIL_ALUNO, aluno.getEmail());
        
        return alunoJSON.build().toString();
    }
    
    @GET
    @Path("/getAlunoNome/{nome}")
    @Consumes (MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String getAlunoNome(@PathParam("nome") String nome) throws Exception {
        Aluno aluno = Alunos.getAlunoNome(nome);
        
        JsonObjectBuilder alunoJSON = Json.createObjectBuilder(JsonValue.EMPTY_JSON_OBJECT);
        
        alunoJSON.add(NOME_ALUNO, aluno.getNome());
        alunoJSON.add(RA_ALUNO, aluno.getRA());
        alunoJSON.add(EMAIL_ALUNO, aluno.getEmail());
        
        return alunoJSON.build().toString();
    }

    @PUT
    @Path("/putAluno")
    @Consumes(MediaType.APPLICATION_JSON)
    public void putAluno(String aluno) throws Exception {
        Aluno aluno_obj = parseJSONAluno(aluno);
        
        Alunos.alterar(aluno_obj);
    }
    
    @POST
    @Path("/includeAluno")
    @Consumes(MediaType.APPLICATION_JSON)
    public void includeAluno(String aluno) throws Exception {
        Aluno aluno_obj = parseJSONAluno(aluno);
        
        Alunos.incluir(aluno_obj);
    }
    
    @POST
    @Path("/deleteAluno")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteAluno(String ra) throws Exception {
        Alunos.excluir(ra);
    }
    
    private Aluno parseJSONAluno(String json) {
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject alunoJSON = reader.readObject();
        
        Aluno a = new Aluno(alunoJSON.getString(RA_ALUNO), alunoJSON.getString(NOME_ALUNO), alunoJSON.getString(EMAIL_ALUNO));
        return a;
    }
    
    /**
     * Retrieves representation of an instance of aluno.AlunoResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of AlunoResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
