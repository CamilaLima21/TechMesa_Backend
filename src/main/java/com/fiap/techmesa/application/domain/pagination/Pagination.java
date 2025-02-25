package com.fiap.techmesa.application.domain.pagination;

import java.util.List;
import java.util.function.Function;

public record Pagination<T>(int number, int size, int totalPages, long totalElements, int numberOfElemensts,
        List<T> content) {

    public <R> Pagination<R> mapContent(final Function<T, R> mapper) {
        return new Pagination<>(number, size, totalPages, totalElements, numberOfElemensts,
                content.stream().map(mapper).toList());
    }

}
