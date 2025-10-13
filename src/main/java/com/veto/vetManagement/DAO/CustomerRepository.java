package com.veto.vetManagement.DAO;

import com.veto.vetManagement.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // İster: Hayvan sahipleri isme göre filtrelenecek.
    // Spring Data JPA bu metot isminden ne yapması gerektiğini anlar.
    // "İsmi verilen string'i içeren (büyük/küçük harf duyarsız) tüm müşterileri bul"
    List<Customer> findByNameContainingIgnoreCase(String name);

    // Veri kaydederken bu telefon numarası zaten var mı diye kontrol için.
    boolean existsByPhone(String phone);

    // Veri kaydederken bu mail adresi zaten var mı diye kontrol için.
    boolean existsByMail(String mail);
}