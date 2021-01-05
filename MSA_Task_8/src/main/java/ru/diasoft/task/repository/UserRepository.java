package ru.diasoft.task.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.diasoft.task.entity.QUserEntity;
import ru.diasoft.task.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long>, QuerydslPredicateExecutor<UserEntity>,
        QuerydslBinderCustomizer<QUserEntity> {

    @Override
    default void customize(QuerydslBindings bindings, QUserEntity qEntity) {
        bindings.bind(qEntity.login)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.bind(qEntity.status)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

    Optional<UserEntity> findByLoginEqualsAndStatusEquals(String login, String status);
}
