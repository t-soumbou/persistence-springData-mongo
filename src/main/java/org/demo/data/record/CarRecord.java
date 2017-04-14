package org.demo.data.record;

import java.io.Serializable;
import javax.validation.constraints.*;


/**
 * Java bean for entity "Car" <br>
 * Contains only "wrapper types" (no primitive types) <br>
 * Can be used both as a "web form" and "persistence record" <br>
 *
 */
public class CarRecord implements Serializable
{
    private static final long serialVersionUID = 1L;

    @NotNull
    private Integer id ; // int // Id or Primary Key

    @NotNull
    @Size( min = 1 )
    private String lastname ;  // String 
    @NotNull
    @Size( min = 1 )
    private String firstname ;  // String 

    /**
     * Default constructor
     */
    public CarRecord() {
        super();
    }
    
    //----------------------------------------------------------------------
    // GETTER(S) & SETTER(S) FOR ID OR PRIMARY KEY 
    //----------------------------------------------------------------------
    /**
     * Set the "id" field value
     * This field is mapped on the database column "id" ( type "", NotNull : true ) 
     * @param id
     */
	public void setId( Integer id ) {
        this.id = id ;
    }
    /**
     * Get the "id" field value
     * This field is mapped on the database column "id" ( type "", NotNull : true ) 
     * @return the field value
     */
	public Integer getId() {
        return this.id;
    }

    //----------------------------------------------------------------------
    // GETTER(S) & SETTER(S) FOR OTHER DATA FIELDS 
    //----------------------------------------------------------------------

    /**
     * Set the "lastname" field value
     * This field is mapped on the database column "lastname" ( type "", NotNull : true ) 
     * @param lastname
     */
    public void setLastname( String lastname ) {
        this.lastname = lastname;
    }
    /**
     * Get the "lastname" field value
     * This field is mapped on the database column "lastname" ( type "", NotNull : true ) 
     * @return the field value
     */
    public String getLastname() {
        return this.lastname;
    }

    /**
     * Set the "firstname" field value
     * This field is mapped on the database column "firstname" ( type "", NotNull : true ) 
     * @param firstname
     */
    public void setFirstname( String firstname ) {
        this.firstname = firstname;
    }
    /**
     * Get the "firstname" field value
     * This field is mapped on the database column "firstname" ( type "", NotNull : true ) 
     * @return the field value
     */
    public String getFirstname() {
        return this.firstname;
    }

    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    @Override
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(id);
        sb.append("|");
        sb.append(lastname);
        sb.append("|");
        sb.append(firstname);
        return sb.toString(); 
    } 



}
