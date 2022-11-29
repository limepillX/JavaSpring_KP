package com.pspkp.kpmain.repo;

import com.pspkp.kpmain.models.Message;

import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
