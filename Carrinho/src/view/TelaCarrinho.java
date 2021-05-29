package view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import model.DAO;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaCarrinho extends JFrame {
	private JPanel contentPane;
	private JLabel lblStatus;
	private JTextField textId;
	private JTextField textCod;
	private JTextField textProd;
	private JTextField textQuant;
	private JTextField textVal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCarrinho frame = new TelaCarrinho();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	public TelaCarrinho() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				// Status da conexão
				status();
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblId = new JLabel("Id:");
		lblId.setBounds(10, 24, 22, 14);
		contentPane.add(lblId);

		JLabel lblCod = new JLabel("C\u00F3digo:");
		lblCod.setBounds(153, 24, 46, 14);
		contentPane.add(lblCod);

		JLabel lblProduto = new JLabel("Produto:");
		lblProduto.setBounds(10, 53, 65, 14);
		contentPane.add(lblProduto);

		JLabel lblQuant = new JLabel("Quantidade:");
		lblQuant.setBounds(10, 94, 85, 14);
		contentPane.add(lblQuant);

		JLabel lblVal = new JLabel("Valor:");
		lblVal.setBounds(217, 97, 46, 14);
		contentPane.add(lblVal);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(TelaCarrinho.class.getResource("/icones/dberror.png")));
		lblStatus.setBounds(390, 217, 32, 32);
		contentPane.add(lblStatus);

		textId = new JTextField();
		textId.setBounds(73, 21, 46, 20);
		contentPane.add(textId);
		textId.setColumns(10);

		textCod = new JTextField();
		textCod.setBounds(200, 21, 166, 20);
		contentPane.add(textCod);
		textCod.setColumns(10);

		textProd = new JTextField();
		textProd.setBounds(73, 50, 293, 20);
		contentPane.add(textProd);
		textProd.setColumns(10);

		textQuant = new JTextField();
		textQuant.setBounds(83, 91, 116, 20);
		contentPane.add(textQuant);
		textQuant.setColumns(10);

		textVal = new JTextField();
		textVal.setBounds(261, 94, 105, 20);
		contentPane.add(textVal);
		textVal.setColumns(10);

		btnPesquisar = new JButton("");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar();
			}
		});
		btnPesquisar.setEnabled(false);
		btnPesquisar.setToolTipText("Pesquisar Produto");
		btnPesquisar.setIcon(new ImageIcon(TelaCarrinho.class.getResource("/icones/read.png")));
		btnPesquisar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPesquisar.setBorder(null);
		btnPesquisar.setBackground(SystemColor.control);
		btnPesquisar.setBounds(30, 141, 48, 48);
		contentPane.add(btnPesquisar);

		btnAdd = new JButton("");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdd.setEnabled(false);
		btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdd.setBorder(null);
		btnAdd.setBackground(SystemColor.control);
		btnAdd.setToolTipText("Adicionar contato");
		btnAdd.setIcon(new ImageIcon(TelaCarrinho.class.getResource("/icones/create.png")));
		btnAdd.setBounds(110, 180, 64, 64);
		contentPane.add(btnAdd);

		btnEdit = new JButton("");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		btnEdit.setEnabled(false);
		btnEdit.setToolTipText("Editar contato");
		btnEdit.setIcon(new ImageIcon(TelaCarrinho.class.getResource("/icones/update.png")));
		btnEdit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEdit.setBorder(null);
		btnEdit.setBackground(SystemColor.control);
		btnEdit.setBounds(175, 180, 64, 64);
		contentPane.add(btnEdit);

		btnDel = new JButton("");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletar();
			}
		});
		btnDel.setEnabled(false);
		btnDel.setBorder(null);
		btnDel.setToolTipText("Excluir contato");
		btnDel.setIcon(new ImageIcon(TelaCarrinho.class.getResource("/icones/delete.png")));
		btnDel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDel.setBackground(SystemColor.control);
		btnDel.setBounds(241, 180, 64, 64);
		contentPane.add(btnDel);

	} // Fim do construtor

	DAO dao = new DAO();
	private JButton btnPesquisar;
	private JButton btnAdd;
	private JButton btnDel;
	private JButton btnEdit;

	/**
	 * Status da conexão
	 */
	private void status() {
		try {
			// estabelecer uma conexão
			Connection con = dao.Conectar();
			// status
			// System.out.println(con);
			// trocando o ícone do banco(status de conexão)
			if (con != null) {
				lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/dbok.png")));
				btnPesquisar.setEnabled(true);
			} else {
				lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/dberror.png")));
			}
			// encerrar conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private void pesquisar() {
		// instrução sql para pesquisar
		String read = "select * from carrinho where barcode = ?";
		try {
			// estabelecer uma conexão
			Connection con = dao.Conectar();
			// preparar a instrução sql
			PreparedStatement pst = con.prepareStatement(read);
			// substituir o parametro
			pst.setString(1, textCod.getText());
			// resultado
			ResultSet rs = pst.executeQuery();
			// se existir um contato correspondente
			if (rs.next()) {
				textId.setText(rs.getString(2));
				textCod.setText(rs.getString(1)); 
				textProd.setText(rs.getString(3));
				textQuant.setText(rs.getString(4));
				textVal.setText(rs.getString(5));
				btnEdit.setEnabled(true);
				btnDel.setEnabled(true);
				btnPesquisar.setEnabled(false);
			} else {
				JOptionPane.showMessageDialog(null, "Contato inexistente");
				btnAdd.setEnabled(true);
				btnPesquisar.setEnabled(false);
				con.close();
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void editar() {
		// validação dos campos
		if (textProd.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do Produto");

		} else if (textProd.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o quantidade do Produto");

		} else if (textProd.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo nome do Produto não pode ter mais que 50 caracteres");

		} else if (textQuant.getText().length() > 15) {
			JOptionPane.showMessageDialog(null, "O campo quantidade não pode ter mais que 15 caracteres");
		} else if (textVal.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo valor não pode ter mais que 50 caracteres");
		} else {
			String update = "update carrinho set Produto=?, Quantidade=?, Valor=? where Codigo=?";

			try {
				// estabelecer uma conexão
				Connection con = dao.Conectar();
				// preparar a instrução sql
				PreparedStatement pst = con.prepareStatement(update);
				// subistituir os parametros (?,?,?) pelo conteudo das caixas de texto
				pst.setString(1, textProd.getText());
				pst.setString(2, textQuant.getText());
				pst.setString(3, textVal.getText());
				pst.setString(4, textCod.getText());
				// executar a query (insert no banco de dados)
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Produto editado com sucesso");
				con.close();
				limpar();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * inserir um novo contato CRUD Creat
	 */
	private void adicionar() {
		// validação dos campos
		if (textProd.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do Produto");

		} else if (textProd.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o quantidade do Produto");

		} else if (textProd.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo nome do Produto não pode ter mais que 50 caracteres");

		} else if (textQuant.getText().length() > 15) {
			JOptionPane.showMessageDialog(null, "O campo quantidade não pode ter mais que 15 caracteres");
		} else if (textVal.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo valor não pode ter mais que 50 caracteres");
		} else {
			String create = "insert into carrinho (barcode, produto,quantidade) values (?,?,?)";

			try {
				// estabelecer uma conexão
				Connection con = dao.Conectar();
				// preparar a instrução sql
				PreparedStatement pst = con.prepareStatement(create);
				// subistituir os parametros (?,?,?) pelo conteudo das caixas de texto
				pst.setString(1, textCod.getText());
				pst.setString(2, textProd.getText());
				pst.setString(3, textQuant.getText());
				// executar a query (insert no banco de dados)
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Produto adicionado com sucesso");
				con.close();
				limpar();

			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	/**
	 * Excluir contato CRUD Delete
	 */
	private void deletar() {
		String delete = "delete from carrinho where  codigo=?";
		// Confirmação de exclusão
		int confirma = JOptionPane.showConfirmDialog(null, "confirma a exclusão desse produto?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			try {
				Connection con = dao.Conectar();
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, textCod.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Contato excluido");
				limpar();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			limpar();
		}
	}

	/**
	 * Limpar campos e configurar os botoes
	 */
	private void limpar() {
		textCod.setText(null);
		textProd.setText(null);
		textQuant.setText(null);
		textVal.setText(null);
		btnAdd.setEnabled(false);
		btnEdit.setEnabled(false);
		btnDel.setEnabled(false);
		btnPesquisar.setEnabled(true);

	}
}