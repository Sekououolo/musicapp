package entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;


@Entity                    // Dit à Spring : "cette classe = une table MySQL"
@Table(name = "artistes")  // Nom de la table dans la base
@Getter                    // Lombok génère tous les getters automatiquement
@Setter                    // Lombok génère tous les setters
@NoArgsConstructor         // Lombok génère un constructeur vide (obligatoire pour JPA)
@AllArgsConstructor        // Lombok génère un constructeur avec tous les paramètres
@Builder

public class Artiste {

    @Id                                              // C'est la clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment (1, 2, 3...)
    private Long id;

    @Column(nullable = false)                        // Cette colonne ne peut pas être vide
    private String nom;

    private String nationalite;

    private String biographie;

    // Un artiste a PLUSIEURS albums
    // mappedBy = "artiste" : c'est la variable dans Album qui fait le lien
    // cascade : si on supprime un artiste, ses albums sont supprimés aussi
    // fetch LAZY : les albums ne sont chargés que quand on en a besoin (performance)

    @OneToMany(mappedBy = "artiste", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Album> albums;

}
