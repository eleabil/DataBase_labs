import com.bilianska.*;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class Main {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {
        final Session session = getSession();
        try {
            showMenu(session);

        } finally {
            session.close();
        }
    }

    public static void showMenu(Session session) {
        System.out.println("  Menu:" +
                "\n1. Select all data with table" +
                "\n2. Insert data to table" +
                "\n3. Delete album in table" +
                "\n4. Update data in table" +
                "\n5. Select M:M(album : singer)" +
                "\n6. Insert and delete(M:M)(album : singer)" +
                "\nAnother number - Exit");

        Scanner scanner = new Scanner(System.in);

        switch (scanner.nextInt()) {
            case 1:
                selectDataWithAllTable(session);

                break;

            case 2:
                System.out.println(
                        "\n1. Insert album" +
                                "\n2. Insert release year" +
                                "\nAnother number - Exit");
                switch (scanner.nextInt()) {
                    case 1:
                        insertAlbum(session);
                        break;

                    case 2:
                        insertReleaseYear(session);
                        break;

                    default:
                        showMenu(session);
                        break;
                }
                break;

            case 3:
                deleteAlbum(session);
                break;

            case 4:
                updateAlbum(session);
                break;

            case 5:
                selectAlbumAndSinger(session);
                break;

            case 6:
                System.out.println(
                        "\n1. Insert data to M:M table" +
                                "\n2. Delete data in M:M table" +
                                "\nAnother number - Exit");

                switch (scanner.nextInt()) {
                    case 1:
                        insertDataToAlbumAndSinger(session);
                        break;

                    case 2:
                        deleteDataWithAlbumAndSinger(session);
                        break;

                    default:
                        showMenu(session);
                        break;
                }
                break;

            default:
                showMenu(session);
                break;
        }

        System.out.println("0 - Show menu" +
                "\nAnother number - Exit");

        switch (scanner.nextInt()) {
            case 0:
                showMenu(session);
                break;

            default:
                break;
        }
    }

    private static void selectDataWithAllTable(Session session) {
        Query query = session.createQuery("from " + "AlbumEntity");
        System.out.format("%3s %-18s %-18s %-18s %s\n", "ID", "name", "genre", "release_year", "label");
        for (Object obj : query.list()) {
            AlbumEntity albumEntity = (AlbumEntity) obj;
            System.out.format("%3d %-18s %-18s %-18s %s\n", albumEntity.getId_album(),
                    albumEntity.getName_album(), albumEntity.getGenre(), albumEntity.getReleaseYearByReleaseYear().getRelease_year(), albumEntity.getLabel());
        }
        System.out.println("\n");

        Query query_2 = session.createQuery("from " + "SingerEntity");
        System.out.format("%3s %-18s %-18s %s\n", "ID", "firstname", "lastname", "country");
        if (query_2.list() == null) {
            System.out.println("Query2 list = null");
        }
        for (Object obj : query_2.list()) {
            SingerEntity book = (SingerEntity) obj;
            System.out.format("%3d %-18s %-18s %s\n", book.getId_singer(), book.getFirstName(), book.getLastName(), book.getCountry());
        }
        System.out.println("\n");

        Query query_3 = session.createQuery("from " + "ReleaseYearEntity");
        for (Object obj : query_3.list()) {
            ReleaseYearEntity releaseYearEntity = (ReleaseYearEntity) obj;
            System.out.format("%s\n", releaseYearEntity.getRelease_year());
        }
        System.out.println("\n");

        System.out.println("Done");
    }

    private static void deleteAlbum(Session session) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input album id:  ");
        Integer id_album = scanner.nextInt();

        AlbumEntity studentEntity = session.load(AlbumEntity.class, id_album);

        if (studentEntity != null) {
            session.beginTransaction();
            Query query = session.createQuery("delete AlbumEntity where id_album=:id_album_code");
            query.setParameter("id_album_code", id_album);
            int result = query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("Done");
        } else {
            System.out.println("Album does not exist");
        }
    }

    private static void updateAlbum(Session session) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nInput an album id: ");
        int id_album = scanner.nextInt();
        System.out.println("Input new genre: ");
        String new_name = scanner.next();

        AlbumEntity studentEntity = session.load(AlbumEntity.class, id_album);
        if (studentEntity != null) {
            session.beginTransaction();
            Query query = session.createQuery("update AlbumEntity set genre=:new_name_code  where id_album = :id_album_code");
            query.setParameter("new_name_code", new_name);
            query.setParameter("id_album_code", id_album);
            int result = query.executeUpdate();
            session.getTransaction().commit();
        } else System.out.println("Album does not exist");

        System.out.println("Done");
    }

    private static void insertReleaseYear(Session session) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input a new release year: ");
        String newYear = scanner.next();

        session.beginTransaction();
        ReleaseYearEntity newReleaseYearEntity = new ReleaseYearEntity(newYear);
        session.save(newReleaseYearEntity);
        session.getTransaction().commit();

        System.out.println("Done");
    }

    public static void deleteDataWithAlbumAndSinger(Session session){
        session.beginTransaction();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input album id: ");
        int albumId = scanner.nextInt();
        System.out.println("Input singer id: ");
        int singerId = scanner.nextInt();

        Query query1 = session.createQuery("from AlbumEntity  where id_album= :id_album_code");
        query1.setParameter("id_album_code", albumId);
        AlbumEntity albumEntity = (AlbumEntity) query1.list().get(0);
        Query query2 = session.createQuery("from SingerEntity where id_singer= :id_singer_code");
        query2.setParameter("id_singer_code", singerId);
        SingerEntity singerEntity = (SingerEntity) query2.list().get(0);
        albumEntity.deleteSingerEntity(singerEntity);
        session.getTransaction().commit();
        System.out.println("Done");
    }

    private static void selectAlbumAndSinger(Session session) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input album id: ");
        Integer albumId = scanner.nextInt();


        Query query = session.createQuery("from AlbumEntity where id_album=:id_album_code");
        query.setParameter("id_album_code", albumId);
        System.out.format("\nM:M --------------------\n");
        for (Object obj : query.list()) {
            AlbumEntity albumEntity = (AlbumEntity) obj;
            System.out.format("%3d %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %s\n", albumEntity.getId_album(),
                    albumEntity.getName_album(), albumEntity.getGenre(), albumEntity.getReleaseYearByReleaseYear().getRelease_year(),
                    albumEntity.getLabel(), " - ", albumEntity.getSingerEntities().get(0).getId_singer(),
                    albumEntity.getSingerEntities().get(0).getFirstName(), albumEntity.getSingerEntities().get(0).getLastName(),
                    albumEntity.getSingerEntities().get(0).getCountry());
        }

        System.out.println("Done");
    }

    public static void insertDataToAlbumAndSinger(Session session){
        session.beginTransaction();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input album id: ");
        Integer albumId = scanner.nextInt();
        System.out.println("Input singer id: ");
        Integer singerId = scanner.nextInt();

        Query query1 = session.createQuery("from AlbumEntity  where id_album= :id_album_code");
        query1.setParameter("id_album_code", albumId);
        AlbumEntity albumEntity = (AlbumEntity) query1.list().get(0);
        Query query2 = session.createQuery("from SingerEntity  where id_singer= :id_singer_code");
        query2.setParameter("id_singer_code", singerId);
        SingerEntity singerEntity = (SingerEntity) query2.list().get(0);
        albumEntity.addSingerEntity(singerEntity);
        session.getTransaction().commit();
        System.out.println("Done");
    }

    private static void insertAlbum(Session session) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input new album name: ");
        String name_album = scanner.next();
        System.out.println("Input new genre: ");
        String genre = scanner.next();
        System.out.println("Input the release year: ");
        String year = scanner.next();
        System.out.println("Input new label: ");
        String label = scanner.next();

        session.beginTransaction();
        AlbumEntity albumEntity = new AlbumEntity(name_album, genre, label, new ReleaseYearEntity(year));
        session.save(albumEntity);
        session.getTransaction().commit();
        System.out.println("Done");
    }

}