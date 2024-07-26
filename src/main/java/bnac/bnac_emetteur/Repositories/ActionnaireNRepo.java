package bnac.bnac_emetteur.Repositories;

import bnac.bnac_emetteur.Entities.Actionnaire_N;
import bnac.bnac_emetteur.Entities.Emetteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ActionnaireNRepo extends JpaRepository<Actionnaire_N,Integer> {

    @Query("select count(Matricule) from Actionnaire_N where emetteur.idEmetteur = :idEmetteur")
    public int VerifActionnaire(@Param("idEmetteur") String idEmetteur);

    @Query("select Matricule from Actionnaire_N where emetteur.idEmetteur = :idEmetteur order by Matricule desc limit 1")
    public int GetLastMatricule(@Param("idEmetteur") String idEmetteur);

    @Transactional
    @Modifying
    @Query("DELETE FROM Actionnaire_N a WHERE a.emetteur = :emetteur")
    void deleteAllByemetteur(@Param("emetteur") Emetteur emetteur);

    @Query("select a from Actionnaire_N a where a.Matricule = :matricule and a.emetteur = :emetteur")
    Actionnaire_N findActionnaire_NByMatriculeAndEmetteur(@Param("matricule") int matricule, @Param("emetteur") Emetteur emetteur);
}
