package ru.practicum.shareit.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.Collection;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    Collection<ItemRequest> findByRequestorIdNotOrderByCreatedDesc(Long requestorId);

    @Query("select distinct r from ItemRequest r " +
            "left join fetch r.items " +
            "where r.requestor.id = ?1 " +
            "order by r.created desc")
    Collection<ItemRequest> findUserRequests(Long userId);
}
