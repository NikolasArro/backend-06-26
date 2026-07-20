package ee.nikolas.backend0626.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double sum;
    private Date created;
    private String parcelMachine;
    private boolean paid;

    @ManyToOne
    private Person person;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderRow> rows;
}
