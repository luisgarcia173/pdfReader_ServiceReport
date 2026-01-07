# pdfReader_ServiceReport

Aplicação **Java 21** que lê um **relatório em PDF**, extrai texto com **OCR (Tesseract via Tess4J)** e realiza **cálculos** a partir das informações extraídas, exibindo os resultados no **console**.

## Stack usada

- Java 21
- Maven
- PDFBox (`org.apache.pdfbox:pdfbox`)
- Tess4J (`net.sourceforge.tess4j:tess4j`) — wrapper Java do Tesseract OCR
- Lombok (`org.projectlombok:lombok`) — utilitários (getters/setters/builders etc.)

## Pré-requisitos

### 1) Java 21
Instale e garanta que `java -version` retorna 21.

### 2) Tesseract OCR instalado no sistema
O **Tess4J depende do Tesseract instalado na máquina**.

- **Windows**: instale o Tesseract e garanta que o `tesseract.exe` esteja no PATH (ou configure o caminho no código).
- **Linux (Debian/Ubuntu)**:
  ```bash
  sudo apt update
  sudo apt install tesseract-ocr
