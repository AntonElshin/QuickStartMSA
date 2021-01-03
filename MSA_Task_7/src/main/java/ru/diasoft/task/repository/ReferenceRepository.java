package ru.diasoft.task.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.diasoft.task.entity.QReferenceEntity;
import ru.diasoft.task.entity.ReferenceEntity;

import java.util.Optional;

public interface ReferenceRepository extends PagingAndSortingRepository<ReferenceEntity, Long>, QuerydslPredicateExecutor<ReferenceEntity>,
        QuerydslBinderCustomizer<QReferenceEntity> {

    @Override
    default void customize(QuerydslBindings bindings, QReferenceEntity qEntity) {
        bindings.bind(qEntity.name)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.bind(qEntity.sysname)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

    Optional<ReferenceEntity> findBySysnameEquals(String sysname);

}
