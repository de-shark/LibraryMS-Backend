package me.deshark.lms.domain.model.borrowing.entity;

import me.deshark.lms.common.utils.GUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatronTest {

    @Test
    void canBorrow_WithValidConditions_ShouldReturnTrue() {
        // Arrange
        Patron patron = new Patron(GUID.v7(), 2, 80);

        // Act & Assert
        assertTrue(patron.canBorrow());
    }

    @Test
    void canBorrow_WithMaxBorrows_ShouldReturnFalse() {
        // Arrange
        Patron patron = new Patron(GUID.v7(), 5, 80);

        // Act & Assert
        assertFalse(patron.canBorrow());
    }

    @Test
    void canBorrow_WithLowCreditScore_ShouldReturnFalse() {
        // Arrange
        Patron patron = new Patron(GUID.v7(), 2, 50);

        // Act & Assert
        assertFalse(patron.canBorrow());
    }

    @Test
    void incrementCurrentBorrows_ShouldIncreaseBorrowCount() {
        // Arrange
        Patron patron = new Patron(GUID.v7(), 2, 80);
        int initialBorrows = patron.getCurrentBorrows();

        // Act
        patron.incrementCurrentBorrows();

        // Assert
        assertEquals(initialBorrows + 1, patron.getCurrentBorrows());
    }

    @Test
    void decrementCurrentBorrows_ShouldDecreaseBorrowCount() {
        // Arrange
        Patron patron = new Patron(GUID.v7(), 2, 80);
        int initialBorrows = patron.getCurrentBorrows();

        // Act
        patron.decrementCurrentBorrows();

        // Assert
        assertEquals(initialBorrows - 1, patron.getCurrentBorrows());
    }
} 