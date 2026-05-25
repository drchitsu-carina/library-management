package com.library.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parabike")
@Data // Lombok: Getter, Setter, toString, EqualsAndHashCode တို့ကို အလိုအလျောက် ထုတ်ပေးသည်
@NoArgsConstructor
@AllArgsConstructor
public class Parabike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "no", length = 50)
    private String no;

    @Column(name = "lib_own_no")
    private String libOwnNo;

    @Column(name = "name", columnDefinition = "TEXT")
    private String name;

    @Column(name = "begin", length = 50)
    private String begin;

    @Column(name = "end", length = 50)
    private String end;

    // Database တွင် plainInk-borderedLeaf ဟု အမည်ပေးထားသောကြောင့် annotation သုံးရန်လိုအပ်သည်
    @Column(name = "plainInk-borderedLeaf", length = 100)
    private String plainInkBorderedLeaf;

    @Column(name = "goldLeaf-sugarCame", length = 100)
    private String goldLeafSugarCame;

    @Column(name = "remark", columnDefinition = "TEXT")
    private String remark;
}