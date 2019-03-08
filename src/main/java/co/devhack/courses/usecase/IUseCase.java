package co.devhack.courses.usecase;

public interface IUseCase<T> {

    T execute() throws Exception;
}
