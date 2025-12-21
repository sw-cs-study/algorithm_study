const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const n = Number(input[0]);
const people = input[1].split(" ");
const data = {};
for (let name of people) {
  data[name] = {
    indegree: 0,
    descendant: [],
  };
}

const m = Number(input[2]);
const answer = [];
// O(N)
for (let i = 0; i < m; i++) {
  let [des, anc] = input[3 + i].split(" ");
  data[des].indegree++;
  data[anc].descendant.push(des);
}
// ancestor 먼저 찾기 = indegree가 0
const ancestors = [];
for (let name of people) {
  let obj = data[name];
  if (obj.indegree === 0) {
    ancestors.push(name);
  }
}
answer.push(ancestors.length);
ancestors.sort();
answer.push(ancestors.join(" "));

const store = []; // [이름, 자식수, 자식]
for (let anc of ancestors) {
  let queue = [anc];
  while (queue.length > 0) {
    let cur = queue.shift();
    // 현재 사람이 자손이 없으면
    if (data[cur].descendant.length === 0) {
      store.push([cur, 0]);
      continue;
    }
    // 현재 사람이 자손이 있으면 진입차수가 1차이나는지 확인하고 처리
    let curIndegree = data[cur].indegree;
    let children = [];
    for (let des of data[cur].descendant) {
      let desIndegree = data[des].indegree;
      if (desIndegree === curIndegree + 1) {
        children.push(des);
        queue.push(des);
      }
    }
    children.sort();
    store.push([cur, children.length, children.join(" ")]);
  }
}
store.sort((a, b) => a[0].localeCompare(b[0]));
for (let el of store) {
  answer.push(el.join(" "));
}

console.log(answer.join("\n"));
