package br.com.drive.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JoinColumnOrFormula;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Vehicle extends AbstractEntity {
    private String licensePlate;
    private BigDecimal kbbPrice;
    private BigDecimal adPrice;

    @ManyToOne
    @JoinColumn(name = "model_year_id")
    private ModelYear modelYear;

}
