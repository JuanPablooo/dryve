package br.com.drive.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Model extends AbstractEntity{
    private String name;


    @ManyToOne
    @JoinColumn(name = "brand_id", foreignKey=@ForeignKey(name= "brand_to_model_fk"))
    private Brand brand;

    @OneToMany(mappedBy = "model")
    @JsonIgnore
    private Set<ModelYear> modelYear = new LinkedHashSet<>();
}
