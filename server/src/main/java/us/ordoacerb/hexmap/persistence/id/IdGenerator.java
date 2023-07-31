package us.ordoacerb.hexmap.persistence.id;

@FunctionalInterface
public interface IdGenerator {
    String generate();
}
