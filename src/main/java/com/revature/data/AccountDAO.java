package com.revature.data;

import com.revature.beans.Account;

public interface AccountDAO {
	
	int addAccount(Account account);
	Account getAccount(String username, String password);
}

/**

package com.revature.data;

import java.util.Set;

import com.revature.beans.Author;
import com.revature.beans.Book;

public interface AuthorDAO {
	int addAuthor(Author a);
	Author getAuthor(int id);
	Author getAuthorByName(String firstname, String lastname);
	Set<Author> getAuthors();
	Set<Author> getAuthorsByBook(Book b);
	void updateAuthor(Author a);
	void deleteAuthor(Author a);
}

**/