#include "header.h"  // Include the header file where your methods are declared

// Test case for criarOuVerificarDiretorio
TEST(DirectoryCreationTest, Test1) {
    EXPECT_EQ(0, criarOuVerificarDiretorio("test_directory"));
}

TEST(DirectoryCreationTest, Test2) {
    EXPECT_EQ(0, criarOuVerificarDiretorio("existing_directory"));
}

// Test case for verificar_argumentos
TEST(ArgumentValidationTest, Test1) {
    char* argv[] = {"program_name", "arg1", "nonexistent_directory", "-1"};
    EXPECT_EQ(1, verificar_argumentos(4, argv));
}

TEST(ArgumentValidationTest, Test2) {
    char* argv[] = {"program_name", "arg1", "existing_directory", "5"};
    EXPECT_EQ(0, verificar_argumentos(4, argv));
}

// Test case for linhas_fConfig
TEST(FileLineCountTest, Test1) {
    EXPECT_EQ(5, linhas_fConfig("sample_file.txt"));
}

// Test case for ler_linha
TEST(ReadLineTest, Test1) {
    char line[] = "1,2,Type1,Unit1,45.67#";
    SensorData result = ler_linha(line);

    EXPECT_EQ(1, result.sensor_id);
    EXPECT_EQ(2, result.write_counter);
    EXPECT_STREQ("Type1", result.type);
    EXPECT_STREQ("Unit1", result.unit);
    EXPECT_DOUBLE_EQ(45.67, result.mediana);
}


int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}