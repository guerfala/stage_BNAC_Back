package bnac.bnac_emetteur.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="NatureCompteTitre")
public class NatureCompteTitre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int IdNatureCompteTitre;

    @Column(length =64,nullable = false)
    private String Libelle;

    @Column(length =2,nullable = false)
    private String CodeNatureCompteTitre;

    @Column(length =64,nullable = true)
    private String codeNT;

    @Column(length =64,nullable = true)
    private String LibelleNT;

}
