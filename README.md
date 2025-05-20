# 📚 Biblioteca Virtual Inteligente

Uma aplicação Java de terminal que simula uma biblioteca virtual com funcionalidades completas, como empréstimo e devolução de livros, lista de espera, histórico de navegação e recomendações inteligentes usando grafos.

## 🚀 Funcionalidades

- ✅ Adicionar e remover livros
- 🔍 Buscar livros por título
- 📃 Listar todos os livros cadastrados
- 📦 Empréstimo e devolução de livros
- ⏳ Gerenciamento de lista de espera
- 🧠 Sistema de recomendação baseado em autor, gênero e ano de publicação (utilizando grafos)
- 🕘 Histórico de navegação
- 📈 Algoritmo de Dijkstra simplificado para recomendações

## 🛠️ Tecnologias Utilizadas

- Java (console)
- Estrutura de dados: `LinkedList`, `Queue`, `Deque`, `Map`, `Set`, `Graph`

## 📂 Estrutura do Projeto

- `BibliotecaVirtual.java`: Classe principal com interface de usuário (menu e interações)
- `Book.java`: Modelo de livro com dados, status e fila de espera
- `BookGraph.java`: Lógica de grafo para relações entre livros e recomendações

## 📥 Como Executar

1. Clone o repositório:

```bash
git clone https://github.com/seu-usuario/biblioteca-virtual-inteligente.git
cd biblioteca-virtual-inteligente
```

2. Compile os arquivos Java:

```bash
javac BibliotecaVirtual.java
```

3. Execute a aplicação:

```bash
java BibliotecaVirtual
```

## 💡 Sobre o Sistema de Recomendação

A aplicação usa um grafo onde livros são conectados se tiverem:

- O mesmo autor
- O mesmo gênero
- Ano de publicação próximo (±10 anos)

Com isso, o algoritmo de Dijkstra simples calcula a menor "distância" entre livros, gerando sugestões personalizadas.

## ✍️ Autor

Projeto desenvolvido como parte de atividade acadêmica.  
Aluno: Lucas Gabriel Kuligowski  
Curso: Análise e Desenvolvimento de Sistemas – Pontifícia Universidade Católica do Paraná
