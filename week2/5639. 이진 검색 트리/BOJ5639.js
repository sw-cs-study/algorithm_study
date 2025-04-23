const input = require('fs').readFileSync(process.platform === "linux" ? '/dev/stdin' : 'sample.txt').toString().trim().split('\n');

class Node {
  constructor(root, value) {
    this.root = root;
    this.value = value;
    this.leftChild = null;
    this.rightChild = null;
  }

  push(value) {
    if (value < this.value) {
      if (this.leftChild) { // 있으면 다음 자식으로 넘김
        this.leftChild.push(value);
      }
      else { this.leftChild = new Node(this, value); }// 없으면 생성
    }
    else {
      if (this.rightChild) {
        this.rightChild.push(value);
      }
      else { this.rightChild = new Node(this, value); }
    }
  }

  print() {
    if (this.leftChild) { this.leftChild.print(); }
    if (this.rightChild) { this.rightChild.print(); }
    if (this.value > 0) { console.log(this.value); }
  }
}

class Tree {
  constructor() {
    this.root = null;
    this.start = null;
  }

  push(value) {
    if (this.start) {
      this.start.push(value);
    } else {
      this.start = new Node(value);
    }
  }

  print() {
    this.start.print();
  }
}

const tree = new Node(null, -1);
for (let i = 0; i < 10000; i++) {
  if (input[i] === undefined) { break; }
  tree.push(Number(input[i]));
}

tree.print();

