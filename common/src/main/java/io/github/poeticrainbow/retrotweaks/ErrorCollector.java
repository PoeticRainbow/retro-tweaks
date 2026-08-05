package io.github.poeticrainbow.retrotweaks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class ErrorCollector {
    // todo maybe remove this, feels weird
    private static final List<Supplier<Optional<String>>> ERROR_CHECKS = new ArrayList<>();

    public static void addErrorCheck(Supplier<Optional<String>> check) {
        ERROR_CHECKS.add(check);
    }

    public static List<String> checkForErrors() {
        return ERROR_CHECKS.stream()
                           .map(Supplier::get)
                           .filter(Optional::isPresent)
                           .map(Optional::get)
                           .toList();
    }
}
