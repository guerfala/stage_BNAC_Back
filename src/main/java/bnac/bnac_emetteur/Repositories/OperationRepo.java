package bnac.bnac_emetteur.Repositories;

import bnac.bnac_emetteur.DTO.JournalDTO;
import bnac.bnac_emetteur.DTO.MouvementsDTO;
import bnac.bnac_emetteur.DTO.SoldeDTO;
import bnac.bnac_emetteur.Entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OperationRepo extends JpaRepository<Operation,Integer> {

    @Query("Select new bnac.bnac_emetteur.DTO.MouvementsDTO(tc.LibelleCourt, cast(o.DateBourse as date), a.RaisonSociale, o.NumContrat, Cast((CASE WHEN o.typeOperation.IdTypeOperation='VC'THEN o.Quantite ELSE '0' END) AS integer)," +
            "cast((CASE WHEN o.typeOperation.IdTypeOperation='AC'THEN o.Quantite ELSE '0' END) AS integer ), round(o.Cours,3)) " +
            "from Actionnaire a, Titre t, Operation o, TeneurCompte tc " +
            "Where a.Matricule=o.actionnaire.Matricule " +
            "and t.IdTitre=o.titre.IdTitre " +
            "and t.emetteur.idEmetteur=a.emetteur.idEmetteur  " +
            "and o.teneurCompte.IdTC=tc.IdTC " +
            "and o.actionnaire.Matricule=a.Matricule " +
            "and o.typeOperation.IdTypeOperation in ('AC','VC') " +
            "and o.titre.IdTitre= :idTitre  " +
            "and o.DateBourse between :minDate and :maxDate")
    List<MouvementsDTO> findAllMouvements(@Param("idTitre") String idTitre, @Param("minDate") LocalDateTime minDate, @Param("maxDate") LocalDateTime maxDate);

    @Query("Select new bnac.bnac_emetteur.DTO.MouvementsDTO(tc.LibelleCourt, cast(o.DateBourse as date), a.RaisonSociale, o.NumContrat, Cast((CASE WHEN o.typeOperation.IdTypeOperation='VC'THEN o.Quantite ELSE '0' END) AS integer)," +
            "cast((CASE WHEN o.typeOperation.IdTypeOperation='AC'THEN o.Quantite ELSE '0' END) AS integer ), round(o.Cours,3)) " +
            "from Actionnaire a, Titre t, Operation o, TeneurCompte tc " +
            "Where a.Matricule=o.actionnaire.Matricule " +
            "and t.IdTitre=o.titre.IdTitre " +
            "and t.emetteur.idEmetteur=a.emetteur.idEmetteur  " +
            "and o.teneurCompte.IdTC=tc.IdTC " +
            "and o.actionnaire.Matricule=a.Matricule " +
            "and o.typeOperation.IdTypeOperation in ('AC','VC') " +
            "and o.titre.IdTitre= :idTitre  " +
            "and o.teneurCompte.IdTC= :idTC  " +
            "and o.DateBourse between :minDate and :maxDate")
    List<MouvementsDTO> findAllMouvementsByTc(@Param("idTitre") String idTitre, @Param("minDate") LocalDateTime minDate, @Param("maxDate") LocalDateTime maxDate, @Param("idTC") String idTC);

    @Query("Select new bnac.bnac_emetteur.DTO.MouvementsDTO(tc.LibelleCourt, cast(o.DateBourse as date), o.IdOperation, o.NumContrat, (CASE WHEN o.typeOperation.IdTypeOperation='VC'THEN o.Quantite ELSE '0' END),(CASE WHEN o.typeOperation.IdTypeOperation='AC'THEN o.Quantite ELSE '0' END), o.Cours) " +
            "from Actionnaire a, Titre t, Operation o, TeneurCompte tc " +
            "Where a.Matricule=o.actionnaire.Matricule " +
            "and t.IdTitre=o.titre.IdTitre " +
            "and t.emetteur.idEmetteur=a.emetteur.idEmetteur " +
            "and o.teneurCompte.IdTC=tc.IdTC " +
            "and o.actionnaire.Matricule=a.Matricule " +
            "and o.typeOperation.IdTypeOperation in ('AC','VC') " +
            "and o.titre.IdTitre= :idTitre " +
            "and o.actionnaire.Matricule= :matricule " +
            "and o.DateBourse between :minDate and :maxDate")
    List<MouvementsDTO> findAllMouvementsByMatricule(@Param("idTitre") String idTitre, @Param("minDate") LocalDateTime minDate, @Param("maxDate") LocalDateTime maxDate, @Param("matricule") int matricule);

    @Query("Select new bnac.bnac_emetteur.DTO.MouvementsDTO(tc.LibelleCourt, cast(o.DateBourse as date), o.IdOperation, o.NumContrat, (CASE WHEN o.typeOperation.IdTypeOperation='VC'THEN o.Quantite ELSE '0' END),(CASE WHEN o.typeOperation.IdTypeOperation='AC'THEN o.Quantite ELSE '0' END), o.Cours ) " +
            "from Actionnaire a, Titre t, Operation o, TeneurCompte tc " +
            "Where a.Matricule=o.actionnaire.Matricule " +
            "and t.IdTitre=o.titre.IdTitre " +
            "and t.emetteur.idEmetteur=a.emetteur.idEmetteur " +
            "and o.teneurCompte.IdTC= tc.IdTC " +
            "and o.actionnaire.Matricule=a.Matricule " +
            "and o.typeOperation.IdTypeOperation in ('AC','VC') " +
            "and o.titre.IdTitre= :idTitre " +
            "and o.actionnaire.Matricule= :matricule " +
            "and o.DateBourse between :minDate and :maxDate " +
            "and o.teneurCompte.IdTC= :idTC ")
    List<MouvementsDTO> findAllMouvementsByMatriculeAndTc(@Param("idTitre") String idTitre, @Param("minDate") LocalDateTime minDate, @Param("maxDate") LocalDateTime maxDate, @Param("matricule") int matricule, @Param("idTC") String idTC);

    @Query("Select new bnac.bnac_emetteur.DTO.SoldeDTO(a.Matricule, a.RaisonSociale, sum(s.Solde)) " +
            "from Actionnaire a,Solde s, Titre t " +
            "Where a.Matricule=s.Matricule " +
            "and t.IdTitre=s.titre.IdTitre " +
            "and t.emetteur.idEmetteur=a.emetteur.idEmetteur " +
            "and s.IdTitre= :idTitre " +
            "and s.dateMaj <= :DateMaj " +
            "and s.actionnaire.Matricule= :Matricule " +
            "group by a.Matricule, a.RaisonSociale,t.Nombre")
    SoldeDTO findActionnaireMouvement(@Param("idTitre") String idTitre, @Param("DateMaj") LocalDateTime DateMaj, @Param("Matricule") int Matricule);

    @Query("Select new bnac.bnac_emetteur.DTO.JournalDTO(tc.LibelleCourt, cast(o.DateBourse as date), tyop.Libelle,a.RaisonSociale,o.titre.IdTitre, o.NumContrat, (CASE WHEN tyop.Sens= -1 THEN o.Quantite ELSE 0 END),(CASE WHEN tyop.Sens= 1 THEN o.Quantite ELSE 0 END)) " +
            "from Actionnaire a, Titre t, Operation o, TeneurCompte tc,TypeOperation tyop " +
            "Where a.Matricule=o.actionnaire.Matricule " +
            "and t.IdTitre=o.titre.IdTitre " +
            "and t.emetteur.idEmetteur=a.emetteur.idEmetteur " +
            "and o.typeOperation.IdTypeOperation=tyop.IdTypeOperation " +
            "and o.teneurCompte.IdTC=tc.IdTC " +
            "and o.actionnaire.Matricule=a.Matricule " +
            "and o.titre.IdTitre= :idTitre  " +
            "and o.DateBourse between :minDate and :maxDate")
    List<JournalDTO> findAllJournals(@Param("idTitre") String idTitre, @Param("minDate") LocalDateTime minDate, @Param("maxDate") LocalDateTime maxDate);

    @Query("Select new bnac.bnac_emetteur.DTO.JournalDTO(tc.LibelleCourt, cast(o.DateBourse as date), tyop.Libelle,a.RaisonSociale,o.titre.IdTitre, o.NumContrat, (CASE WHEN tyop.Sens= -1 THEN o.Quantite ELSE 0 END),(CASE WHEN tyop.Sens= 1 THEN o.Quantite ELSE 0 END)) " +
            "from Actionnaire a, Titre t, Operation o, TeneurCompte tc,TypeOperation tyop " +
            "Where a.Matricule=o.actionnaire.Matricule " +
            "and t.IdTitre=o.titre.IdTitre " +
            "and t.emetteur.idEmetteur=a.emetteur.idEmetteur " +
            "and o.typeOperation.IdTypeOperation=tyop.IdTypeOperation " +
            "and o.teneurCompte.IdTC= tc.IdTC " +
            "and o.actionnaire.Matricule=a.Matricule " +
            "and o.titre.IdTitre= :idTitre  " +
            "and o.DateBourse between :minDate and :maxDate " +
            "and o.teneurCompte.IdTC = :idTc")
    List<JournalDTO> findAllJournalsByTc(@Param("idTitre") String idTitre, @Param("minDate") LocalDateTime minDate, @Param("maxDate") LocalDateTime maxDate, @Param("idTc") String idTc);

    @Query("Select new bnac.bnac_emetteur.DTO.JournalDTO(tc.LibelleCourt, cast(o.DateBourse as date), tyop.Libelle,a.RaisonSociale,o.titre.IdTitre, o.NumContrat, (CASE WHEN tyop.Sens= -1 THEN o.Quantite ELSE 0 END),(CASE WHEN tyop.Sens= 1 THEN o.Quantite ELSE 0 END)) " +
            "from Actionnaire a, Titre t, Operation o, TeneurCompte tc,TypeOperation tyop " +
            "Where a.Matricule=o.actionnaire.Matricule " +
            "and t.IdTitre=o.titre.IdTitre " +
            "and t.emetteur.idEmetteur=a.emetteur.idEmetteur " +
            "and o.typeOperation.IdTypeOperation= tyop.IdTypeOperation " +
            "and o.teneurCompte.IdTC= tc.IdTC " +
            "and o.actionnaire.Matricule=a.Matricule " +
            "and o.titre.IdTitre= :idTitre  " +
            "and o.DateBourse between :minDate and :maxDate " +
            "and o.typeOperation.IdTypeOperation= :typeOp ")
    List<JournalDTO> findAllJournalsByTypeOperation(@Param("idTitre") String idTitre, @Param("minDate") LocalDateTime minDate, @Param("maxDate") LocalDateTime maxDate, @Param("typeOp") String typeOp);

    @Query("Select new bnac.bnac_emetteur.DTO.JournalDTO(tc.LibelleCourt, cast(o.DateBourse as date), tyop.Libelle,a.RaisonSociale,o.titre.IdTitre, o.NumContrat, (CASE WHEN tyop.Sens= -1 THEN o.Quantite ELSE 0 END),(CASE WHEN tyop.Sens= 1 THEN o.Quantite ELSE 0 END)) " +
            "from Actionnaire a, Titre t, Operation o, TeneurCompte tc,TypeOperation tyop " +
            "Where a.Matricule=o.actionnaire.Matricule " +
            "and t.IdTitre=o.titre.IdTitre " +
            "and t.emetteur.idEmetteur=a.emetteur.idEmetteur " +
            "and o.typeOperation.IdTypeOperation= tyop.IdTypeOperation " +
            "and o.teneurCompte.IdTC= tc.IdTC " +
            "and o.actionnaire.Matricule=a.Matricule " +
            "and o.titre.IdTitre= :idTitre  " +
            "and o.DateBourse between :minDate and :maxDate " +
            "and o.typeOperation.IdTypeOperation= :typeOp " +
            "and o.teneurCompte.IdTC= :idTc")
    List<JournalDTO> findAllJournalsByTypeOperationAndTc(@Param("idTitre") String idTitre, @Param("minDate") LocalDateTime minDate, @Param("maxDate") LocalDateTime maxDate, @Param("typeOp") String typeOp, @Param("idTc") String idTc);
    
    @Query("Select new bnac.bnac_emetteur.DTO.JournalDTO(tc.LibelleCourt, cast(o.DateBourse as date), tyop.Libelle,o.IdOperation, o.NumContrat, (CASE WHEN tyop.Sens= -1 THEN o.Quantite ELSE 0 END),(CASE WHEN tyop.Sens= 1 THEN o.Quantite ELSE 0 END)) " +
            "from Actionnaire a, Titre t, Operation o, TeneurCompte tc,TypeOperation tyop " +
            "Where a.Matricule=o.actionnaire.Matricule " +
            "and t.IdTitre=o.titre.IdTitre " +
            "and t.emetteur.idEmetteur=a.emetteur.idEmetteur " +
            "and o.typeOperation.IdTypeOperation=tyop.IdTypeOperation  " +
            "and o.teneurCompte.IdTC=tc.IdTC " +
            "and o.actionnaire.Matricule=a.Matricule " +
            "and o.titre.IdTitre= :idTitre " +
            "and o.actionnaire.Matricule= :matricule " +
            "and o.DateBourse between :minDate and :maxDate")
    List<JournalDTO> findAllJournalsByMatricule(@Param("idTitre") String idTitre, @Param("minDate") LocalDateTime minDate, @Param("maxDate") LocalDateTime maxDate, @Param("matricule") int matricule);

    @Query("Select new bnac.bnac_emetteur.DTO.JournalDTO(tc.LibelleCourt, cast(o.DateBourse as date), tyop.Libelle,o.IdOperation, o.NumContrat, (CASE WHEN tyop.Sens= -1 THEN o.Quantite ELSE 0 END),(CASE WHEN tyop.Sens= 1 THEN o.Quantite ELSE 0 END)) " +
            "from Actionnaire a, Titre t, Operation o, TeneurCompte tc,TypeOperation tyop " +
            "Where a.Matricule=o.actionnaire.Matricule " +
            "and t.IdTitre=o.titre.IdTitre " +
            "and t.emetteur.idEmetteur=a.emetteur.idEmetteur " +
            "and o.typeOperation.IdTypeOperation=tyop.IdTypeOperation  " +
            "and o.teneurCompte.IdTC=tc.IdTC " +
            "and o.actionnaire.Matricule=a.Matricule " +
            "and o.titre.IdTitre= :idTitre " +
            "and o.actionnaire.Matricule= :matricule " +
            "and o.DateBourse between :minDate and :maxDate " +
            "and o.teneurCompte.IdTC= :idTc")
    List<JournalDTO> findAllJournalsByMatriculeAndTc(@Param("idTitre") String idTitre, @Param("minDate") LocalDateTime minDate, @Param("maxDate") LocalDateTime maxDate, @Param("matricule") int matricule, @Param("idTc") String idTc);

    @Query("Select new bnac.bnac_emetteur.DTO.JournalDTO(tc.LibelleCourt, cast(o.DateBourse as date), tyop.Libelle,o.IdOperation, o.NumContrat, (CASE WHEN tyop.Sens= -1 THEN o.Quantite ELSE 0 END),(CASE WHEN tyop.Sens= 1 THEN o.Quantite ELSE 0 END)) " +
            "from Actionnaire a, Titre t, Operation o, TeneurCompte tc,TypeOperation tyop " +
            "Where a.Matricule=o.actionnaire.Matricule " +
            "and t.IdTitre=o.titre.IdTitre " +
            "and t.emetteur.idEmetteur=a.emetteur.idEmetteur " +
            "and o.typeOperation.IdTypeOperation=tyop.IdTypeOperation  " +
            "and o.teneurCompte.IdTC=tc.IdTC " +
            "and o.actionnaire.Matricule=a.Matricule " +
            "and o.titre.IdTitre= :idTitre " +
            "and o.actionnaire.Matricule= :matricule " +
            "and o.DateBourse between :minDate and :maxDate " +
            "and o.typeOperation.IdTypeOperation= :typeOp")
    List<JournalDTO> findAllJournalsByMatriculeAndTypeOp(@Param("idTitre") String idTitre, @Param("minDate") LocalDateTime minDate, @Param("maxDate") LocalDateTime maxDate, @Param("matricule") int matricule, @Param("typeOp") String typeOp);

    @Query("Select new bnac.bnac_emetteur.DTO.JournalDTO(tc.LibelleCourt, cast(o.DateBourse as date), tyop.Libelle,o.IdOperation, o.NumContrat, (CASE WHEN tyop.Sens= -1 THEN o.Quantite ELSE 0 END),(CASE WHEN tyop.Sens= 1 THEN o.Quantite ELSE 0 END)) " +
            "from Actionnaire a, Titre t, Operation o, TeneurCompte tc,TypeOperation tyop " +
            "Where a.Matricule=o.actionnaire.Matricule " +
            "and t.IdTitre=o.titre.IdTitre " +
            "and t.emetteur.idEmetteur=a.emetteur.idEmetteur " +
            "and o.typeOperation.IdTypeOperation=tyop.IdTypeOperation  " +
            "and o.teneurCompte.IdTC=tc.IdTC " +
            "and o.actionnaire.Matricule=a.Matricule " +
            "and o.titre.IdTitre= :idTitre " +
            "and o.actionnaire.Matricule= :matricule " +
            "and o.DateBourse between :minDate and :maxDate " +
            "and o.typeOperation.IdTypeOperation= :typeOp " +
            "and o.teneurCompte.IdTC= :idTc")
    List<JournalDTO> findAllJournalsByMatriculeAndTcAndTypeOp(@Param("idTitre") String idTitre, @Param("minDate") LocalDateTime minDate, @Param("maxDate") LocalDateTime maxDate, @Param("matricule") int matricule, @Param("typeOp") String typeOp, @Param("idTc") String idTc);

}
