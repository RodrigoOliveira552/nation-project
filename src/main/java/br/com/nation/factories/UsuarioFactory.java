package br.com.nation.factories;

import br.com.nation.models.Usuario;
import net.datafaker.Faker;
import java.util.Locale;

public class UsuarioFactory {

    private static final Faker faker = new Faker(new Locale("pt", "BR"));

    public static Usuario criarUsuarioAdminValido() {
        return Usuario.builder()
                .nome(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .administrador("true")
                .build();
    }

    public static Usuario criarUsuarioComumValido() {
        return Usuario.builder()
                .nome(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .administrador("false")
                .build();
    }
}