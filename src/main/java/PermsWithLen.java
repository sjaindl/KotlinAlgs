
class Solution {
    Set<Group> result = Sets.newHashSet();

    public boolean processLine(String line) {
        if (line.contains("[") && line.contains("]")) {
            String values = line.substring(1, line.length() - 1);
            List<Long> groupMembers = Arrays
                    .stream(values.split(" "))
                    .map(value -> Long.valueOf(value))
                    .collect(Collectors.toList());

            permsWithLen(groupMembers, groupSize);
            //                        result.addAll();
        }

        return false;
    }

    public void permsWithLen(List<Long> array, int desiredLen) {
        if (array.size() < desiredLen) {
            return;
        }

        List<Long> current = new ArrayList<>();
        this.permsWithLenRecursive(array, desiredLen, 0, current);
    }

    private void permsWithLenRecursive(List<Long> array, int desiredLen, int curIndex, List<
            Long> current) {
        if (current.size() == desiredLen) {
            Group group = new Group();
            group.addAll(current);
            result.add(group);
        } else if (curIndex < array.size() && current.size() < desiredLen) {
            this.permsWithLenRecursive(array, desiredLen, curIndex + 1, current);

            current.add(array.get(curIndex));
            this.permsWithLenRecursive(array, desiredLen, curIndex + 1, current);
            current.remove(current.size() - 1);
        }
    }

    public Set<Group> getResult() {
        return result;
    }
}
