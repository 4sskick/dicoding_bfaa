package com.niteroomcreation.beginermade.fragment.main;

import com.niteroomcreation.beginermade.R;
import com.niteroomcreation.beginermade.base.BasePresenter;
import com.niteroomcreation.beginermade.model.TokohModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Septian Adi Wijaya on 01/10/19
 */
public class MainFragmentPresenter extends BasePresenter<MainFragmentContract.View> implements MainFragmentContract.Presenter {

    public MainFragmentPresenter(MainFragmentContract.View view) {
        super.onViewActive(view);
    }

    @Override
    public void onViewActive(MainFragmentContract.View view) {

    }

    @Override
    public void onViewInactive() {

    }

    public List<TokohModel> constructModels() {
        List<TokohModel> tokoh = new ArrayList<>();

        tokoh.add(new TokohModel("Dr. Khoirul Anwar"
                , "Penemu konsep dua FFT, yang dipakai dalam teknologi 4G LTE. Tahun 2010 Dr. Eng Khoirul Anwar menemukan konsep dua Fast Fourier Transform (FFT) yang kemudian menjadi standar International Telecommunication Union (ITU). Temuannya ini berhasil meningkatkan kecepatan transmisi data. ITU adalah salah satu pembentuk komponen teknologi LTE. Hingga saat ini Dr. Eng Khoirul Anwar telah memiliki tiga paten dalam dunia telekomunikasi."
                , R.drawable.satu));
        tokoh.add(new TokohModel("Dr. Yogi Ahmad Erlangga"
                , "Penemu rumus matematika berdasarkan persamaan Helmholtz, membuat pencarian sumber minyak bumi lebih cepat dan akurat. Dr. Yogi Ahmad Erlangga dinobatkan sebagai doktor matematika terapan di Belanda. Dirinya dikenal oleh dunia karena berhasil memecahkan persamaan Helmholtz yang digunakan untuk pencarian minyak bumi. Penemuannya ini membuat ribuan insinyur minyak bekerja lebih cepat dengan akurasi yang tinggi pula."
                , R.drawable.dua));
        tokoh.add(new TokohModel("Dr. Warsito Purwo Taruno"
                , "Penemu alat pemindai (ECVT) 4 dimensi (4D). Selain sebagai ketua Masyarakat Ilmuwan Teknolog Indonesia (MITI) dan ketua dewan penasehat Institute for Science and Technology Studies, Dr. Warsito Purwo Taruno adalah pemilik paten Electrical Capacitance Volume Tomography (ECVT). Ia penemu yang mengembangkan ECVT 4 dimensi pertama di dunia, yang digunakan untuk melakukan pemindaian pada dinding pesawat ulang-alik."
                , R.drawable.tiga));
        tokoh.add(new TokohModel("Prof. Josaphat T. S. Sumantyo"
                , "Penemu radar 3 dimensi. Prof. Josaphat T. S. Sumantyo menemukan radar 3 dimensi yang sangat membantu dalam mengirim informasi untuk navigasi pesawat. Penemuannya ini tersebar di beberapa negara di dunia dan sering digunakan untuk memprediksi cuaca. Banyak penghargaan sudah diraihnya berkat dedikasinya di bidang antena, sensor, dan radar, di antaranya adalah Nanohana Venture Competition Award, Nanohana Competition Award hingga Chiba University President Award."
                , R.drawable.empat));
        tokoh.add(new TokohModel("Dr. Eniya Listiani Dewi"
                , "Penemu membran sel bahan bakar menggunakan unsur Vanadium. Salah satu peneliti di Badan Pengkajian dan Penerapan Teknologi (BPPT) ini menemukan sel bahan bakar baru yang menggunakan unsur Vanadium. Penemuannya ini membuat dirinya mendapat penghargaan Mizuno Award dan Koukenkai Award dari Waseda University (tempat ia menempuh pendidikan hingga S3) di tahun 2003."
                , R.drawable.lima));
        tokoh.add(new TokohModel("Dr. Johny Setiawan"
                , "Penemu planet baru HIP 13044 b. Dr. Johny Setiawan adalah seorang astronom lulusan doktor termuda di Albert Ludwigs University of Freiburg, Jerman. Sejak 2003, ia adalah satu-satunya ilmuwan non Jerman yang menjadi Ketua Tim Proyek Max Planck Institute for Astronomy, di Heidelberg, Baden-Wurttemberg, Jerman. Ilmuwan ini dikenal dunia karena menemukan planet baru yang diberi nama HIP 13044 b. Selain itu, ia juga menemukan planet HD 47536 b, HD 11977 b, HD 47536 c, HD 70573 b, HD 110014 b, hingga TW Hydrae b."
                , R.drawable.enam));
        tokoh.add(new TokohModel("Prof. Mezak Arnold Ratag"
                , "Penemu lebih dari 100 buah Planetary Nebulae. Banyak sekali karya dan prestasi yang telah diraih oleh Prof. Mezak Arnold Ratag dan sangat dihargai di tingkat dunia. Salah satu kebanggaannya adalah sebagai penemu lebih dari 100 buah planetary nebulae (PN) baru. Namanya bahkan diabadikan di 120 Planetary Nebula Cluster, termasuk Ratag-Ziljstra-Pottasch-Menzies dan Ratag-Pottasch cluster."
                , R.drawable.tujuh));
        tokoh.add(new TokohModel("Mulyoto Pangestu, Ph.D"
                , "Penemu teknik pengeringan dan penyimpanan sperma dalam suhu ruang. Ilmuwan yang satu ini berhasil menemukan teknik pengeringan (evaporative drying) dan penyimpanan sperma dalam suhu ruang, serta bisa disimpan selama bertahun-tahun. Penemuan ini sangat berguna bagi para ilmuwan dan dokter di negara berkembang karena selain sebagai alternatif penyimpanan dengan alat pendingin, teknik ini menggunakan bahan-bahan yang tergolong sangat murah."
                , R.drawable.delapan));
        tokoh.add(new TokohModel("Dr. Joe Hin Tjio"
                , "Penemu fakta jumlah kromosom manusia sebanyak 46 buah (23 pasang). Dr. Joe Hin Tjio merupakan ahli genetika kelahiran Pekalongan yang menemukan fakta bahwa jumlah kromosom manusia sebanyak 46 buah, bukan 48 buah. Penemuannya ini mematahkan asumsi yang sudah ada di dunia selama lebih dari 50 tahun yang percaya bahwa manusia normal memiliki 46 buah. Penelitian dilakukan di laboratorium Institute of Genetics of Sweden’s University of Lund dan dipublikasikan di Hereditas, jurnal ilmiah Denmark, Norwegia, dan Swedia, jurnal genetika yang terkemuka saat itu. Ini menjadi penemuan yang sangat penting karena merupakan perhitungan yang pertama dan definitif, terkandung gen yang menentukan garis keturunan seseorang."
                , R.drawable.sembilan));
        tokoh.add(new TokohModel("Profesor B.J. Habibie"
                , "Penemu teori Crack untuk mengetes kualitas pesawat terbang. Mantan presiden RI ini adalah ilmuwan asal Indonesia yang paling dikenal dunia. Ia menemukan teori Crack, menghitung crack propagation on random sampai ke atom-atom pesawat terbang, untuk mengetes kualitas pesawat terbang. Tak hanya itu, ia juga menciptakan pesawat terbang sendiri berjenis CN-235 dan N-250, serta membantu dalam produksi pesawat Eropa berjenis A-300. Nah, sekarang sudah tahu, kan, bahwa Indonesia juga punya orang-orang hebat yang dikenal dan diakui oleh dunia? Semoga kamu terinspirasi ya. Jangan berhenti berkarya!"
                , R.drawable.sepuluh));

        return tokoh;
    }
}
