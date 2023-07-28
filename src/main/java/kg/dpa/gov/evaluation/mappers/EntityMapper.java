package kg.dpa.gov.evaluation.mappers;

public interface EntityMapper<From, To> {
    To map(From entity);
}
