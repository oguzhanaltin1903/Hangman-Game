package Hangman;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Question {

    private HashMap<String, String> questionAnswer;

    public Question() {
        questionAnswer = new HashMap<>();

        questionAnswer.put("Özellikle temel sınıf olarak kullanılmak üzere tasarlanmış bir sınıftır. En az bir saf sanal işlev içerir.", "soyut sınıf");
        questionAnswer.put("Kendi başına bir platform olarak kullanılabilecek çok platformlu, nesne yönelimli ve ağ merkezli bir dildir. Mobil uygulamalardan ve kurumsal yazılımlardan büyük veri uygulamalarına ve sunucu tarafı teknolojilerine kadar her şeyi kodlamak için hızlı, güvenli, güvenilir bir programlama dilidir.", "java");
        questionAnswer.put("Aynı türdeki tüm nesnelerde ortak olan statik nitelikleri ve dinamik davranışları tanımlayan ve tanımlayan bir plan, şablon veya prototiptir.", "sınıf");
        questionAnswer.put("Belirli bir koşulun doğru olduğu değerlendirilirse, programın belirli bir bölümünü art arda yürütmek için kullanılan bir özelliktir.", "döngü");
        questionAnswer.put("Verileri (değişkenleri) ve kodu (yöntemleri) tek bir birime entegre ediyor. İçinde, bir sınıfın değişkenleri diğer sınıflardan gizlenir ve\n yalnızca bulundukları sınıfın yöntemleriyle erişilebilir.", "kapsülleme");
        questionAnswer.put("Sınıfın örneğini oluşturmak için kullanılır.", "yapıcı");
        questionAnswer.put("Veri değerlerini depolamak için bir kapsayıcıdır.", "değişken");
        questionAnswer.put("Derleyici ve yorumlayıcı tarafından yürütülmeyen bir ifadedir. Değişken, yöntem, sınıf veya herhangi bir ifade hakkında bilgi veya açıklama sağlamak için kullanılabilir.", "yorum satırı");
        questionAnswer.put("Diğer sınıflardan miras alarak sınıflar arasında hiyerarşi oluşturma yöntemidir.", "miras");
        questionAnswer.put("Bir alt sınıfın, üst sınıfta zaten mevcut olan yöntem için yöntem uygulamasına sahip olduğu zamandır.", "baskın");
        questionAnswer.put("Bir sınıfta aynı ada sahip birden fazla yöntemi tanımlama yeteneğidir. Derleyici, yöntem imzaları nedeniyle yöntemleri ayırt edebilir.", "aşırı yükleme");
        questionAnswer.put("Bir üyenin erişimini herkese açık olarak bildiren bir Java anahtar kelimesidir", "public");
        questionAnswer.put("Bu bir Java anahtar kelimesidir. İmport deyiminin altındaki kodda kullanılacak bir Java sınıfı bildirir.", "import");
        questionAnswer.put("Artık ihtiyaç duyulmayan veya kullanılmayan otomatik kod silme işlemidir.", "çöp koleksiyonu");
        questionAnswer.put("Bu, bir Java sınıfının bir örneğidir, yani belirli bir sınıfın kopyasıdır..", "obje");
    }

    public HashMap<String, String> getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(HashMap<String, String> questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public void shuffleQuestions() {
        ArrayList<String> questions = new ArrayList<>(questionAnswer.keySet());
        Collections.shuffle(questions);
        HashMap<String, String> shuffledQuestionAnswer = new HashMap<>();
        for (String question : questions) {
            shuffledQuestionAnswer.put(question, questionAnswer.get(question));
        }

        questionAnswer = shuffledQuestionAnswer;
    }

    public String getQuestion(int questionIndex) {
        ArrayList<String> questions = new ArrayList<>(questionAnswer.keySet());
        
        if (questionIndex >= 0 && questionIndex < questions.size()) {
            return questions.get(questionIndex);
        }
        return null;
    }

    public String getAnswer(String currentQuestion) {
        return questionAnswer.get(currentQuestion);
    }
}