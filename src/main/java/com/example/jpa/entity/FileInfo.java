package com.example.jpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "ox_file_info")
public class FileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "file_id")
    private Long fileId;

    @Column(name = "file_name")
    private String fileName;

//    @OneToOne(mappedBy = "fileInfo", fetch = FetchType.LAZY)
//    private Clients clients;
}
