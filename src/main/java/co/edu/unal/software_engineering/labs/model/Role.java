package co.edu.unal.software_engineering.labs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the role database table.
 */
@Entity
@Table( name = "role", schema = "public" )
public class Role implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * Attributes
     */

    //Secuncia inicia en 3 en la db
    @Id
    @SequenceGenerator( name = "ROLE_ROLEID_GENERATOR", sequenceName = "public.role_role_id_seq", allocationSize = 1 )
    @GeneratedValue( generator = "ROLE_ROLEID_GENERATOR", strategy = GenerationType.SEQUENCE )
    @Column( name = "role_id" )
    private Integer id;

    @Column( name = "role_name" )
    private String roleName;

    //bi-directional many-to-many association to User
    @JsonIgnore
    @ManyToMany( mappedBy = "roles" )
    private List<User> users;

    /**
     * Constructors
     */

    public Role( ){ }

    /**
     * Getters and Setters
     */

    public Integer getId( ){
        return this.id;
    }

    public void setId( Integer id ){
        this.id = id;
    }

    public String getRoleName( ){
        return this.roleName;
    }

    public void setRoleName( String roleName ){
        this.roleName = roleName;
    }

    public List<User> getUsers( ){
        return this.users;
    }

    public void setUsers( List<User> users ){
        this.users = users;
    }

    /**
     * Methods
     */

    @Override
    public boolean equals( Object object ){
        if( !(object instanceof Role) ) return false;
        return id.equals( ((Role) object).getId( ) );
    }

    @Override
    public int hashCode( ){
        return id;
    }

}